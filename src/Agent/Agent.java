package Agent;

import java.io.File;
import static java.lang.System.exit;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Agent {

    private Queue<Node> frontier;
    private Queue<Node> exploredSet;
    private int noOfStates;
    private int noOfActions;
    private int noOfTestCases;
    private String[] statesDescribtion;
    private String[] testCases;
    private String[] possibleActions;
    private int[][] transitionTable;

    public Agent() { //Initializing Variables
        frontier = null;
        exploredSet = null;
        noOfStates = 0;
        noOfActions = 0;
        noOfTestCases = 0;
        statesDescribtion = null;
        testCases = null;
        possibleActions = null;
        transitionTable = null;
    }

    public void getInputFromUser() {
        try { //Applying Input Checks to catch any error in Input.

            System.out.println("\n\033[43mTaking Input From SampleInput1.txt File\033[00m");
            File file = new File("F:\\OOAD\\AI_AGENT_1\\src\\Agent\\sampleInput1.txt");
            frontier = new LinkedList<>();
            exploredSet = new LinkedList<>();
            Scanner sc = new Scanner(file);
            String[] MNT = sc.nextLine().split(" ");
            noOfStates = Integer.parseInt(MNT[0]); //Storing no of States
            noOfActions = Integer.parseInt(MNT[1]); //Storing no of Actions
            noOfTestCases = Integer.parseInt(MNT[2]); //Storing no of Test Cases
            if (sc.nextLine().compareToIgnoreCase("") != 0) { //Check For Empty Line
                throw new Exception();
            }
            statesDescribtion = new String[noOfStates];
            for (int i = 0; i < noOfStates; i = i + 1) { //Storing State Describtion
                statesDescribtion[i] = sc.nextLine();
                if (statesDescribtion[i].compareToIgnoreCase("") == 0 || statesDescribtion[0].length() != statesDescribtion[i].length()) {
                    throw new Exception();
                }
            }
            if (sc.nextLine().compareToIgnoreCase("") != 0) { //Check For Empty Line
                throw new Exception();
            }
            possibleActions = new String[noOfStates];
            for (int i = 0; i < noOfActions; i = i + 1) { //Storing Possible Action
                possibleActions[i] = sc.nextLine();
                if (possibleActions[i].compareToIgnoreCase("") == 0) {
                    throw new Exception();
                }
            }
            if (sc.nextLine().compareToIgnoreCase("") != 0) { //Check For Empty Line
                throw new Exception();
            }
            transitionTable = new int[noOfStates][noOfActions];
            String[] childs;
            int tempAction;
            for (int i = 0; i < noOfStates; i = i + 1) { //Storing Transition Table
                childs = sc.nextLine().split(" ");
                for (int k = 0; k < noOfActions; k = k + 1) {
                    tempAction = Integer.parseInt(childs[k]);
                    if (childs[k].compareToIgnoreCase("") == 0 || childs.length != noOfActions
                            || tempAction < 0 || tempAction > noOfStates - 1) {
                        throw new Exception();
                    }
                    transitionTable[i][k] = tempAction;
                }
            }
            if (sc.nextLine().compareToIgnoreCase("") != 0) { //Check For Empty Line
                throw new Exception();
            }
            testCases = new String[noOfTestCases];
            for (int i = 0; i < noOfTestCases; i = i + 1) { //Storing Test Cases
                testCases[i] = sc.nextLine();
                if (testCases[i].compareToIgnoreCase("") == 0 || testCases[0].length() != testCases[i].length()) {
                    throw new Exception();
                }
            }
            sc.close(); //Ignoring any Further input
        } catch (Exception e) { //To catch any Exception in given Input
            System.out.println("\033[41m\nERROR\033[00m => You are Giving Wrong Input. Please follow input format given in SampleInput Files");
            System.exit(0);
        }
    }

    public void showResult() { //Displaying Result
        for (int i = 0; i < noOfTestCases; i = i + 1) {
            int startState = getStartingState(testCases[i], statesDescribtion);
            int goalState = getGoalState(testCases[i], statesDescribtion);
            Node tempNode = BFS(startState, goalState);
            System.out.print("\n\033[43mTest Case: " + (i + 1) + "\n\033[46mStart\033[47m->");
            if (tempNode == null) {
                System.out.println("\033[41mGoal Not Reachable\033[00m\n");
            } else {
                displayActions(tempNode);
                System.out.println("\033[42mGoal\033[00m\n");
            }
        }
    }

    private void displayActions(Node node) { //Displaying Result
        if (node.getParent() != null) {
            displayActions(node.getParent());
        }
        if (node.getAction() != -1) {
            System.out.print(possibleActions[node.getAction()] + "->");
        }
    }

    private Node BFS(int startState, int goalState) {
        boolean isGoal = false;
        String result = "";
        try { //Applying Input Checks to catch any error in Input.
            for (int i = 0; i < testCases.length; i = i + 1) {
                frontier.clear();
                exploredSet.clear();

                Node tempNode = new Node(startState, -1, 0, null);
                if (goalTest(tempNode, goalState) == true) { //Applying Goal Test
                    return tempNode;
                } else {
                    frontier.add(new Node(startState, -1, 0, null));
                    while (!frontier.isEmpty()) {
                        tempNode = frontier.poll();
                        exploredSet.add(tempNode);
                        for (int j = 0; j < noOfActions; j = j + 1) {
                            Node childNode = new Node(transitionTable[tempNode.state][j], j, tempNode.getPathCost() + 1, tempNode);
                            if (!isElementOfFrontier(childNode, frontier) && !isElementOfExploredSet(childNode, exploredSet)) {
                                if (goalTest(childNode, goalState) == true) { //Applying Goal Test
                                    return childNode;
                                }
                                frontier.add(childNode);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {  //To catch any Exception occur during BFS.
            System.out.println("\033[31m\nERROR\033[00m => You are Giving Wrong Input on which BFS is not Applicable. Please follow input format given in SampleInput Files");
            exit(0);
        }
        return null;
    }

    private int getStartingState(String testCase, String[] stateDescribtion) { //return Starting State 

        String[] states = testCase.split("\t");
        for (int i = 0; i < stateDescribtion.length; i = i + 1) {
            if (states[0].compareToIgnoreCase(stateDescribtion[i]) == 0) {
                return i;
            }
        }
        return -1;
    }

    private int getGoalState(String testCase, String[] stateDescribtion) { //return Goal State

        String[] states = testCase.split("\t");
        for (int i = 0; i < stateDescribtion.length; i = i + 1) {
            if (states[1].compareToIgnoreCase(stateDescribtion[i]) == 0) {
                return i;
            }
        }
        return -1;
    }

    private boolean isElementOfFrontier(Node node, Queue<Node> frontier) { //Check whether the node is in frontier or not.
        int size = frontier.size();
        boolean flag = false;
        Node tempNode;
        for (int i = 0; i < size; i = i + 1) {
            tempNode = frontier.poll();
            if (node.getState() == tempNode.getState()) {
                flag = true;
            }
            frontier.add(tempNode);

        }

        return flag;
    }

    private boolean isElementOfExploredSet(Node node, Queue<Node> exploredSet) { //Check whether the node is in explored set or not.
        int size = exploredSet.size();
        boolean flag = false;
        Node tempNode;
        for (int i = 0; i < size; i = i + 1) {
            tempNode = exploredSet.poll();
            if (node.getState() == tempNode.getState()) {
                flag = true;
            }
            exploredSet.add(tempNode);

        }
        return flag;
    }

    private boolean goalTest(Node node, int goalState) { //Applying Goal Test
        return node.getState() == goalState;
    }
}
