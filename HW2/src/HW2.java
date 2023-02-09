/*
Luke Nordheim
CSC 364
10/5/2021
 */

//importing libraries used
import java.io.*;
import java.util.*;
import java.util.Scanner;

public class HW2 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(System.in);

        //1 Prompts the user for the number of workweeks available (integer)
        System.out.print("Enter the number of available employee work weeks: ");
        int workWeek = input.nextInt();

        //2 Prompts the user for the name of the input file (string)
        System.out.print("Enter the name of input file: ");
        String inputName = input.next();
        String inputFileName = ("src/" + inputName);

        //3 Prompts the user for the name of the output file (string)
        System.out.print("Enter the name of output file: ");

        String outputName = input.next();
        String outputFileName = ("src/" + outputName);

        //4 Reads the available projects from the input file
        Scanner dataFile = new Scanner(new File(inputFileName));
        ArrayList<Project> projects = new ArrayList<>();
        while (dataFile.hasNextLine()){
            String line = dataFile.nextLine();
            String[] split = line.split(" ");
            projects.add(new Project(split[0], Integer.parseInt(split[1]), Integer.parseInt(split[2])));
        }
        dataFile.close();
        input.close();

        PrintWriter outputFile = new PrintWriter(outputFileName);
        outputFile.println("Number of projects available: " + projects.size());
        outputFile.println("Available employee work weeks: " + workWeek);

        /*
        System.out.print("Number of projects = ");
        int numProjects = input.nextInt();
         */

        System.out.print("Done :)");

        //This is where you start finding the most efficient projects
        //5 Solves the corresponding knapsack problem, without repetition of items

        int val[] = new int[projects.size()]; //profit
        int wt[] = new int[projects.size()]; //weight
        // wt[j-1] -> projects.get(j-1).workweek
        // val[j-1] -> projects.get(j-1).profit

        int W = workWeek;
        int n = val.length;
        int K[][] = new int[W+1][n+1];
        for (int i = 0; i < projects.size(); i++) {
            val[i] = projects.get(i).getProfit();
            wt[i] = projects.get(i).getWorkweek();
        }

        for (int j = 1 ; j < (n + 1) ; j++){
            for ( int w = 0 ; w < (W+1) ; w++){
                if ( wt[j-1] > w){
                    K[w][j] = K[w][j-1];
                }
                else{
                    K[w][j] = max(K[w-wt[j-1]][j-1]+val[j-1],K[w][j-1]);
                }
            }
        }

        int total = K[W][n];

        /*
        for(int w=0 ; w<(W+1) ; w++){
            for(int j=0 ; j<(n+1) ; j++){
                System.out.print(K[w][j]+" ");
            }
            System.out.println();
        }
         */

        ArrayList<Project> finalProjects = new ArrayList<>();
        int p = W;
        int result = total;

        for (int i = n; result > 0 && i > 0; i--) {
            if (result == K[p][i-1]) {
                continue;
            }
            else {
                int weight = projects.get(i - 1).getWorkweek();
                int value = projects.get(i - 1).getProfit();
                String projectName = projects.get(i - 1).getProject();
                finalProjects.add(new Project(projectName, weight, value));

                result = result - val[i - 1];
                p = p - wt[i - 1];
            }
        }

        outputFile.println("Number of projects chosen: " + finalProjects.size());
        outputFile.println("Total profit: " + total);

        //6 Writes to the output file a summary of the results, including the expected profit and a list of the bestprojects for the company to undertake.
        for (int i = finalProjects.size(); i > 0; i--) {
            outputFile.println(finalProjects.get(i - 1).getProject() + " " + finalProjects.get(i - 1).getWorkweek() + " " + finalProjects.get(i - 1).getProfit());
        }

        outputFile.close();
    }

    //max method used in sort
    static int max(int a, int b) {
        return (a > b) ? a : b;
    }

    private static class Project {
        String project;
        int workweek;
        int profit;
        public Project(String project, int workweek, int profit) {
            super();
            this.project = project;
            this.workweek = workweek;
            this.profit = profit;
        }
        public String getProject() {
            return project;
        }
        public int getWorkweek() {
            return workweek;
        }
        public int getProfit() {
            return profit;
        }

        public void setProject(String project) { this.project = project; }
        public void setWorkweek(int workweek) { this.workweek = workweek; }
        public void setProfit(int profit) { this.profit = profit; }
    }
}
