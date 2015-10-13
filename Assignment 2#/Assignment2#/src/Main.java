import java.io.*;
import java.util.Random;

/**
 * @author Alik Khilazhev
 * @email a.khilazhev@innopolis.ru
 * @date September 29, 2015
 * In accordance with the academic honor, I Alik Khilazhev certify that
 * the answers here are my own work without copying of others and
 * solutions from Internet or other sources."
 */

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
            int n = Integer.parseInt(reader.readLine());
            for (int i = 0; i < n; i++) {
                int teamCount = Integer.parseInt(reader.readLine());

                Team[] teams = new Team[teamCount];

                for (int j = 0; j < teamCount; j++)
                    teams[j] = new Team(reader.readLine());

                int games = Integer.parseInt(reader.readLine());
                /** Parsing games's data*/
                for (int j = 0; j < games; j++) {
                    String[] gameData = reader.readLine().split("#");
                    String firstTeamName = gameData[0],
                            secondTeamName = gameData[2];
                    String[] goals = gameData[1].split("@");

                    int firstTeamGoals = Integer.parseInt(goals[0]);
                    int secondTeamGoals = Integer.parseInt(goals[1]);
                    Team first = findTeamByName(teams, firstTeamName);
                    Team second = findTeamByName(teams, secondTeamName);
                    first.playGame(firstTeamGoals, secondTeamGoals);
                    second.playGame(secondTeamGoals, firstTeamGoals);
                }

                MegaLibrary.quickSort(teams, 0, teamCount - 1);
                int rank = 0;
                for (Team team : teams){
                    String print = String.format("%d) %s %dp, %dg (%d-%d-%d), %dgd (%d-%d)\n",
                            ++rank, team.name, team.score, team.gamePlayed(), team.wins, team.ties, team.loses,
                            team.goalDifference(), team.goalsScored, team.goalsAgainst);
                    writer.write(print);
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
    private static Team findTeamByName(Team[] teams, String name){
        Team result = null;
        for(Team team: teams) {
            if (team.name.compareTo(name) == 0) {
                result = team;
                break;
            }
        }
        return result;
    }

    static class MegaLibrary {

        /***
         * Function for sorting array with quick sort method
         * @param array array to sort
         * @param minIndex starting index
         * @param maxIndex ending index
         */
        public static void quickSort(Comparable[] array, int minIndex ,int maxIndex){
            Random random = new Random();
            int supportIndex = random.nextInt(maxIndex - minIndex + 1) + minIndex;
            Comparable supportValue = array[supportIndex];
            Comparable temp;
            int l = minIndex, r = maxIndex;
            while (l <= r){
                while(array[l].compareTo(supportValue) > 0)
                    l++;
                while(array[r].compareTo(supportValue) < 0)
                    r--;
                if(l <= r)
                {
                    temp = array[l];
                    array[l] = array[r];
                    array[r] = temp;
                    l++;
                    r--;
                }
            }
            if(r > minIndex)
                quickSort(array, minIndex, r);
            if(l < maxIndex)
                quickSort(array, l, maxIndex);
        }
    }
}

class Team implements Comparable{
    public String name;
    public Integer wins;
    public Integer loses;
    public Integer ties;
    public Integer score;
    public Integer goalsScored;
    public Integer goalsAgainst;

    public Team(String name){
        this.name = name;
        wins = 0;
        loses = 0;
        ties = 0;
        score = 0;
        goalsScored = 0;
        goalsAgainst = 0;
    }

    public Integer gamePlayed(){
        return ties + loses + wins;
    }
    public Integer goalDifference(){
        return goalsScored - goalsAgainst;
    }
    public void playGame(int goalsScored, int goalsAgainst){

        this.goalsScored += goalsScored;
        this.goalsAgainst += goalsAgainst;

        if(goalsScored > goalsAgainst) {
            wins++;
            score +=3;
        }

        if(goalsAgainst > goalsScored)
            loses++;

        if(goalsAgainst == goalsScored){
            score++;
            ties++;
        }
    }

    @Override
    public int compareTo(Object o) {
        Team anotherTeam = (Team)o;
        if(score == anotherTeam.score){
            if(wins == anotherTeam.wins){
                if(goalDifference() == anotherTeam.goalDifference()){
                    if(goalsScored == anotherTeam.goalsScored){
                        if(gamePlayed() == anotherTeam.gamePlayed()){
                            return name.toLowerCase().compareTo(anotherTeam.name.toLowerCase());
                        }
                        return gamePlayed().compareTo(anotherTeam.gamePlayed());
                    }
                    return goalsScored.compareTo(anotherTeam.goalsScored);
                }
                return goalDifference().compareTo(anotherTeam.goalDifference());
            }
            return wins.compareTo(anotherTeam.wins);
        }
        return score.compareTo(anotherTeam.score);
    }
}


