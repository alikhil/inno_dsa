import java.io.*;
import java.util.Random;

/**
 * @author Alik Khilazhev
 * @email a.khilazhev@innopolis.ru
 * @date October 11, 2015
 * In accordance with the academic honor, I Alik Khilazhev certify that
 * the answers here are my own work without copying of others and
 * solutions from Internet or other sources."
 */

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
            String line = reader.readLine();
            Team[] teams = new Team[5];
            int teamCount = 0;
            while (line != null && line != "") {
                String[] words = line.split(" ");
                if (words.length == 1){
                    teams[teamCount++] = new Team(words[0]);
                }
                else if (words.length == 2){
                    int firstTeam = Integer.parseInt(words[0]);
                    int secondTeam = Integer.parseInt(words[1]);
                    String[] scores = reader.readLine().split(" ");
                    int firstScore = Integer.parseInt(scores[0]);
                    int secondScore = Integer.parseInt(scores[1]);

                    teams[firstTeam].playGame(firstScore, secondScore);
                    teams[secondTeam].playGame(secondScore, firstScore);
                }
                line = reader.readLine();
            }
            MegaLibrary.selectionSort(teams,0, teamCount - 1);
            for(Team team : teams){
                if(team != null)
                    writer.write(team.name + "\n");
            }
            reader.close();
            writer.close();
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file");
        }
    }


    static class MegaLibrary {

        /***
         * Function for sorting array with quick sort method
         *
         * @param array    array to sort
         * @param minIndex starting index
         * @param maxIndex ending index
         */
        public static void selectionSort(Comparable[] array, int minIndex, int maxIndex) {
            int max = 0;
            Comparable temp;
            for (int i = minIndex; i < maxIndex; i++) {
                max = i;
                for (int j = i; j <= maxIndex; j++) {
                    if(array[j].compareTo(array[max]) > 0)
                        max = j;
                }
                temp = array[i];
                array[i] = array[max];
                array[max] = temp;
            }
        }
    }
}

class Team implements Comparable {
    public String name;
    public Integer wins;
    public Integer score;
    public Integer goalsScored;


    public Team(String name) {
        this.name = name;
        wins = 0;
        score = 0;
        goalsScored = 0;
    }

    public void playGame(int goalsScored, int goalsAgainst) {

        this.goalsScored += goalsScored;

        if (goalsScored > goalsAgainst) {
            wins++;
            score += 3;
        }

        if (goalsAgainst == goalsScored) {
            score++;
        }
    }

    @Override
    public int compareTo(Object o) {
        Team anotherTeam = (Team) o;
        if (score == anotherTeam.score) {
            if (wins == anotherTeam.wins) {
                if (goalsScored == anotherTeam.goalsScored) {
                    return anotherTeam.name.toLowerCase().compareTo(name.toLowerCase());
                }
                return goalsScored.compareTo(anotherTeam.goalsScored);
            }
            return wins.compareTo(anotherTeam.wins);
        }
        return score.compareTo(anotherTeam.score);
    }
}


