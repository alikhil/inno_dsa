import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * @author Alik Khilazhev
 * @email a.khilazhev@innopolis.ru
 * @date October 11, 2015
 * In accordance with the academic honor, I Alik Khilazhev certify that
 * the answers here are my own work without copying of others and
 * solutions from Internet or other sources."
 */

/**
 * Why quicksort?  Because it ont of the fastest sort method and on big input data it will work fine
 */

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("tournament.in"));
            BufferedWriter writer = new BufferedWriter(new FileWriter("tournament.out"));
            String line = reader.readLine();
            ArrayList<Team> teams = new ArrayList<Team>(10);
            while (line != null && line != "") {
                String[] words = line.split(" ");
                if (words.length == 1){
                    teams.add(new Team(words[0]));
                }
                else if (words.length == 2){
                    int firstTeam = Integer.parseInt(words[0]);
                    int secondTeam = Integer.parseInt(words[1]);
                    String[] scores = reader.readLine().split(" ");
                    int firstScore = Integer.parseInt(scores[0]);
                    int secondScore = Integer.parseInt(scores[1]);

                    teams.get(firstTeam).playGame(firstScore, secondScore);
                    teams.get(secondTeam).playGame(secondScore, firstScore);
                }
                line = reader.readLine();
            }
            if(teams.size() > 0) {
                TeamComparator cmp = new TeamComparator();
                MegaLibrary.quickSort(teams, cmp, 0, teams.size() - 1);
                for (Team team : teams) {
                    if (team != null && team.played > 0)
                        writer.write(team.name + "\n");
                }
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
         * Function for sorting list with quick sort method
         *
         * @param list    list to sort
         * @param cmp Comparator
         * @param minIndex start index
         * @param maxIndex end index
         */
        public static <Type> void quickSort(List<Type> list, Comparator<Type> cmp, int minIndex, int maxIndex){
            Random random = new Random();
            int supportIndex = random.nextInt(maxIndex - minIndex + 1) + minIndex;
            Type supportValue = list.get(supportIndex);
            Type temp;
            int l = minIndex, r = maxIndex;
            while (l <= r){
                while(cmp.compare(list.get(l), supportValue) < 0)
                    l++;
                while(cmp.compare(list.get(r), supportValue) > 0)
                    r--;
                if(l <= r)
                {
                    temp = list.get(l);
                    list.set(l, list.get(r));
                    list.set(r, temp);
                    l++;
                    r--;
                }
            }
            if(r > minIndex)
                quickSort(list, cmp, minIndex, r);
            if(l < maxIndex)
                quickSort(list, cmp, l, maxIndex);
        }
    }
}

/**
 * Team entity
 */
class Team{
    public String name;
    public Integer wins;
    public Integer score;
    public Integer goalsScored;
    public Integer played;

    public Team(String name) {
        this.name = name;
        wins = 0;
        score = 0;
        goalsScored = 0;
        played = 0;
    }

    /**
     * play a match
     * @param goalsScored scored goals
     * @param goalsAgainst missed goals
     */
    public void playGame(int goalsScored, int goalsAgainst) {

        this.goalsScored += goalsScored;

        if (goalsScored > goalsAgainst) {
            wins++;
            score += 3;
        }

        if (goalsAgainst == goalsScored) {
            score++;
        }
        played++;
    }

}

/**
 * Teams comparator - compares two teams
 */
class TeamComparator implements Comparator<Team>{

    @Override
    public int compare(Team t, Team t1) {
        if (t1.score == t.score) {
            if (t1.wins == t.wins) {
                if (t1.goalsScored == t.goalsScored) {
                    return t1.name.compareTo(t.name);
                }
                return t1.goalsScored.compareTo(t.goalsScored);
            }
            return t1.wins.compareTo(t.wins);
        }
        return t1.score.compareTo(t.score);
    }
}



