package bearmaps.proj2c.server.handler.impl;

import bearmaps.proj2c.AugmentedStreetMapGraph;
import bearmaps.proj2c.server.handler.APIRouteHandler;
import spark.Request;
import spark.Response;
import bearmaps.proj2c.utils.Constants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static bearmaps.proj2c.utils.Constants.SEMANTIC_STREET_GRAPH;
import static bearmaps.proj2c.utils.Constants.ROUTE_LIST;

/**
 * Handles requests from the web browser for map images. These images
 * will be rastered into one large image to be displayed to the user.
 * @author rahul, Josh Hug, _________
 */
public class RasterAPIHandler extends APIRouteHandler<Map<String, Double>, Map<String, Object>> {

    /**
     * Each raster request to the server will have the following parameters
     * as keys in the params map accessible by,
     * i.e., params.get("ullat") inside RasterAPIHandler.processRequest(). <br>
     * ullat : upper left corner latitude, <br> ullon : upper left corner longitude, <br>
     * lrlat : lower right corner latitude,<br> lrlon : lower right corner longitude <br>
     * w : user viewport window width in pixels,<br> h : user viewport height in pixels.
     **/
    private static final String[] REQUIRED_RASTER_REQUEST_PARAMS = {"ullat", "ullon", "lrlat",
            "lrlon", "w", "h"};

    /**
     * The result of rastering must be a map containing all of the
     * fields listed in the comments for RasterAPIHandler.processRequest.
     **/
    private static final String[] REQUIRED_RASTER_RESULT_PARAMS = {"render_grid", "raster_ul_lon",
            "raster_ul_lat", "raster_lr_lon", "raster_lr_lat", "depth", "query_success"};


    @Override
    protected Map<String, Double> parseRequestParams(Request request) {
        return getRequestParams(request, REQUIRED_RASTER_REQUEST_PARAMS);
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param requestParams Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @param response : Not used by this function. You may ignore.
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image;
     *                    can also be interpreted as the length of the numbers in the image
     *                    string. <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    @Override
    public Map<String, Object> processRequest(Map<String, Double> requestParams, Response response) {
        Map<String, Object> results = new HashMap<>();
        //Calculate the LonDPP of request parameter
        double requestLonDPP=(requestParams.get("lrlon")-requestParams.get("ullon"))/requestParams.get("w");

        //Find the depth of the tiles to be rastered
        double fileLonDPP=(bearmaps.proj2c.utils.Constants.ROOT_LRLON - bearmaps.proj2c.utils.Constants.ROOT_ULLON)
                /bearmaps.proj2c.utils.Constants.TILE_SIZE;
        int depth=0;
        while (fileLonDPP>requestLonDPP){
            fileLonDPP=fileLonDPP/2;
            depth++;
        }
        if (depth>7)
           depth=7;

        //Find the tiles that cover the target area
        int N=(int)Math.pow(2,depth);
        double dlon=(bearmaps.proj2c.utils.Constants.ROOT_LRLON - bearmaps.proj2c.utils.Constants.ROOT_ULLON)/N;
        double dlat=(bearmaps.proj2c.utils.Constants.ROOT_ULLAT - bearmaps.proj2c.utils.Constants.ROOT_LRLAT)/N;
        double tileLonMin,tileLonMax,tileLatMin,tileLatMax,AreaLonMin,AreaLonMax,AreaLatMin,AreaLatMax;
        AreaLonMin=requestParams.get("ullon");
        AreaLonMax=requestParams.get("lrlon");
        AreaLatMin=requestParams.get("lrlat");
        AreaLatMax=requestParams.get("ullat");
        Queue<String> tiles=new ArrayDeque<>();
        int imin=10000,jmin=10000,imax=0,jmax=0;
        for(int i=0;i<N;i++) {
            for (int j = 0; j < N; j++) {
                tileLonMin = bearmaps.proj2c.utils.Constants.ROOT_ULLON + j * dlon;
                tileLonMax = tileLonMin + dlon;
                tileLatMax = bearmaps.proj2c.utils.Constants.ROOT_ULLAT - i * dlat;
                tileLatMin = tileLatMax - dlat;
                if (insideArea(tileLonMin, tileLonMax, tileLatMin, tileLatMax, AreaLonMin, AreaLonMax, AreaLatMin, AreaLatMax)) {
                    if (i>imax)
                        imax=i;
                    if(i<imin)
                        imin=i;
                    if(j>jmax)
                        jmax=j;
                    if(j<jmin)
                        jmin=j;
                    tiles.add("d" + depth + "_x" + j + "_y" + i+".png");
                }
            }
        }
        if((imax<imin)||(jmax<jmin)) {
            results.put("render_grid","");
            results.put("raster_ul_lon",bearmaps.proj2c.utils.Constants.ROOT_ULLON + jmin * dlon);
            results.put("raster_ul_lat",bearmaps.proj2c.utils.Constants.ROOT_ULLAT - imin * dlat);
            results.put("raster_lr_lon",bearmaps.proj2c.utils.Constants.ROOT_ULLON + (jmax+1) * dlon);
            results.put("raster_lr_lat",bearmaps.proj2c.utils.Constants.ROOT_ULLAT - (imax+1) * dlat);
            results.put("depth",depth);
            results.put("query_success", false);
            return results;
        }
        String[][] tileResult=new String[imax-imin+1][jmax-jmin+1];
        for(int i=0;i<=imax-imin;i++) {
            for (int j = 0; j <= jmax - jmin; j++) {
                tileResult[i][j] =tiles.poll();
            }
        }
        results.put("render_grid",tileResult);
        results.put("raster_ul_lon",bearmaps.proj2c.utils.Constants.ROOT_ULLON + jmin * dlon);
        results.put("raster_ul_lat",bearmaps.proj2c.utils.Constants.ROOT_ULLAT - imin * dlat);
        results.put("raster_lr_lon",bearmaps.proj2c.utils.Constants.ROOT_ULLON + (jmax+1) * dlon);
        results.put("raster_lr_lat",bearmaps.proj2c.utils.Constants.ROOT_ULLAT - (imax+1) * dlat);
        results.put("depth",depth);
        results.put("query_success",true);
        return results;
    }

    private boolean insideArea(double tileLonMin,double tileLonMax, double tileLatMin, double tileLatMax,
                               double AreaLonMin,double AreaLonMax,double AreaLatMin, double AreaLatMax){
        //if the lowerleft corner is inside the area
        if ((tileLatMin<AreaLatMax)&&(tileLatMin>AreaLatMin)&&(tileLonMin<AreaLonMax)&&(tileLonMin>AreaLonMin))
            return true;
        //if the lowerright corner is inside the area
        if ((tileLatMin<AreaLatMax)&&(tileLatMin>AreaLatMin)&&(tileLonMax<AreaLonMax)&&(tileLonMax>AreaLonMin))
            return true;
        //if the upperleft corner is inside the area
        if ((tileLatMax<AreaLatMax)&&(tileLatMax>AreaLatMin)&&(tileLonMin<AreaLonMax)&&(tileLonMin>AreaLonMin))
            return true;
        //if the upperright corner is inside the area
        if ((tileLatMax<AreaLatMax)&&(tileLatMax>AreaLatMin)&&(tileLonMax<AreaLonMax)&&(tileLonMax>AreaLonMin))
            return true;

        //if the lowerleft corner of area is inside the tile
        if ((AreaLatMin<tileLatMax)&&(AreaLatMin>tileLatMin)&&(AreaLonMin<tileLonMax)&&(AreaLonMin>tileLonMin))
            return true;
        //if the lowerright corner is inside the area
        if ((AreaLatMin<tileLatMax)&&(AreaLatMin>tileLatMin)&&(AreaLonMax<tileLonMax)&&(AreaLonMax>tileLonMin))
            return true;
        //if the upperleft corner is inside the area
        if ((AreaLatMax<tileLatMax)&&(AreaLatMax>tileLatMin)&&(AreaLonMin<tileLonMax)&&(AreaLonMin>tileLonMin))
            return true;
        //if the upperright corner is inside the area
        if ((AreaLatMax<tileLatMax)&&(AreaLatMax>tileLatMin)&&(AreaLonMax<tileLonMax)&&(AreaLonMax>tileLonMin))
            return true;
        return false;
    }

    @Override
    protected Object buildJsonResponse(Map<String, Object> result) {
        boolean rasterSuccess = validateRasteredImgParams(result);

        if (rasterSuccess) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            writeImagesToOutputStream(result, os);
            String encodedImage = Base64.getEncoder().encodeToString(os.toByteArray());
            result.put("b64_encoded_image_data", encodedImage);
        }
        return super.buildJsonResponse(result);
    }

    private Map<String, Object> queryFail() {
        Map<String, Object> results = new HashMap<>();
        results.put("render_grid", null);
        results.put("raster_ul_lon", 0);
        results.put("raster_ul_lat", 0);
        results.put("raster_lr_lon", 0);
        results.put("raster_lr_lat", 0);
        results.put("depth", 0);
        results.put("query_success", false);
        return results;
    }

    /**
     * Validates that Rasterer has returned a result that can be rendered.
     * @param rip : Parameters provided by the rasterer
     */
    private boolean validateRasteredImgParams(Map<String, Object> rip) {
        for (String p : REQUIRED_RASTER_RESULT_PARAMS) {
            if (!rip.containsKey(p)) {
                System.out.println("Your rastering result is missing the " + p + " field.");
                return false;
            }
        }
        if (rip.containsKey("query_success")) {
            boolean success = (boolean) rip.get("query_success");
            if (!success) {
                System.out.println("query_success was reported as a failure");
                return false;
            }
        }
        return true;
    }

    /**
     * Writes the images corresponding to rasteredImgParams to the output stream.
     * In Spring 2016, students had to do this on their own, but in 2017,
     * we made this into provided code since it was just a bit too low level.
     */
    private  void writeImagesToOutputStream(Map<String, Object> rasteredImageParams,
                                                  ByteArrayOutputStream os) {
        String[][] renderGrid = (String[][]) rasteredImageParams.get("render_grid");
        int numVertTiles = renderGrid.length;
        int numHorizTiles = renderGrid[0].length;

        BufferedImage img = new BufferedImage(numHorizTiles * Constants.TILE_SIZE,
                numVertTiles * Constants.TILE_SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics graphic = img.getGraphics();
        int x = 0, y = 0;

        for (int r = 0; r < numVertTiles; r += 1) {
            for (int c = 0; c < numHorizTiles; c += 1) {
                graphic.drawImage(getImage(Constants.IMG_ROOT + renderGrid[r][c]), x, y, null);
                x += Constants.TILE_SIZE;
                if (x >= img.getWidth()) {
                    x = 0;
                    y += Constants.TILE_SIZE;
                }
            }
        }

        /* If there is a route, draw it. */
        double ullon = (double) rasteredImageParams.get("raster_ul_lon"); //tiles.get(0).ulp;
        double ullat = (double) rasteredImageParams.get("raster_ul_lat"); //tiles.get(0).ulp;
        double lrlon = (double) rasteredImageParams.get("raster_lr_lon"); //tiles.get(0).ulp;
        double lrlat = (double) rasteredImageParams.get("raster_lr_lat"); //tiles.get(0).ulp;

        final double wdpp = (lrlon - ullon) / img.getWidth();
        final double hdpp = (ullat - lrlat) / img.getHeight();
        AugmentedStreetMapGraph graph = SEMANTIC_STREET_GRAPH;
        List<Long> route = ROUTE_LIST;

        if (route != null && !route.isEmpty()) {
            Graphics2D g2d = (Graphics2D) graphic;
            g2d.setColor(Constants.ROUTE_STROKE_COLOR);
            g2d.setStroke(new BasicStroke(Constants.ROUTE_STROKE_WIDTH_PX,
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            route.stream().reduce((v, w) -> {
                g2d.drawLine((int) ((graph.lon(v) - ullon) * (1 / wdpp)),
                        (int) ((ullat - graph.lat(v)) * (1 / hdpp)),
                        (int) ((graph.lon(w) - ullon) * (1 / wdpp)),
                        (int) ((ullat - graph.lat(w)) * (1 / hdpp)));
                return w;
            });
        }

        rasteredImageParams.put("raster_width", img.getWidth());
        rasteredImageParams.put("raster_height", img.getHeight());

        try {
            ImageIO.write(img, "png", os);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private BufferedImage getImage(String imgPath) {
        BufferedImage tileImg = null;
        if (tileImg == null) {
            try {
                File in = new File(imgPath);
                tileImg = ImageIO.read(in);
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
        return tileImg;
    }
}
