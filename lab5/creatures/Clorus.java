package creatures;

import huglife.Action;
import huglife.Creature;
import huglife.Direction;
import huglife.Occupant;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.Random;

public class Clorus extends Creature {
    public Clorus() {
        super("clorus");
    }

    public Clorus(double e) {
        super("clorus");
        energy = e;
    }

    @Override
    public void move() {
        energy -= 0.03;
    }

    @Override
    public void stay() {
        energy -= 0.01;
    }

    @Override
    public Color color() {
        return color(34, 0, 231);
    }

    @Override
    public void attack(Creature c) {
        energy += c.energy();
    }

    @Override
    public Clorus replicate() {
        energy = energy / 2;
        Clorus baby = new Clorus(energy);
        return baby;
    }

    private Direction randomNeighbor(Deque<Direction> emptyNeighbors) {
        if (emptyNeighbors.size() == 1) {
            return emptyNeighbors.getFirst();
        }
        Random ran = new Random();
        int x = ran.nextInt(emptyNeighbors.size() - 1);
        int i = 0;
        while (i < x) {
            emptyNeighbors.removeFirst();
            i++;
        }
        return emptyNeighbors.getFirst();
    }

    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        // Rule 1
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> plipNeighbors = new ArrayDeque<>();
        // TODO
        // (Google: Enhanced for-loop over keys of NEIGHBORS?)
        for (Direction key : neighbors.keySet()) {
            if (neighbors.get(key).name().equals("empty")) {
                emptyNeighbors.add(key);
            }
            if (neighbors.get(key).name().equals("plip")) {
                plipNeighbors.add(key);
            }
        }

        if (emptyNeighbors.size() == 0) {
            return new Action(Action.ActionType.STAY);
        }

        // Rule 2
        if (plipNeighbors.size() > 0) {
            return new Action(Action.ActionType.ATTACK, randomNeighbor(plipNeighbors));
        }

        // Rule 3
        if (energy >= 1) {
            return new Action(Action.ActionType.REPLICATE, randomNeighbor(emptyNeighbors));
        }

        // Rule 4
        return new Action(Action.ActionType.MOVE, randomNeighbor(emptyNeighbors));

    }

}
