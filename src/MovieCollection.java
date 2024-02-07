import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Collections;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        System.out.print("Enter an actor search term: ");
        String searchActor = scanner.nextLine();

        ArrayList <String> actors = new ArrayList<String>();
        ArrayList<String> display = new ArrayList<String>();
        for (int i = 0; i<movies.size(); i++)
        {
            String[] tempActors = movies.get(i).getCast().split("\\|");

            for (int j = 0; j<tempActors.length; j++)
            {
                if (!actors.contains(tempActors[j]))
                    actors.add(tempActors[j]);
            }
        }
        for (String actor: actors)
        {
            if (actor.toLowerCase().contains(searchActor.toLowerCase()))
                display.add(actor);
        }
        Collections.sort(display);
        for (int i = 0; i < display.size(); i++)
        {
            System.out.println(i+1 + ". " + display.get(i));
        }
        System.out.println("Which cast member would you like to learn more about?");
        System.out.print("Enter Number: ");
        String searchNum = scanner.nextLine();
        int num = Integer.parseInt(searchNum);
        ArrayList<Movie> appearedIn = new ArrayList<Movie>();
        for (Movie film : movies)
        {
            if (film.getCast().toLowerCase().contains(display.get(num-1).toLowerCase()))
            {
                appearedIn.add(film);
            }
        }
        sortResults(appearedIn);
        for(int i = 0; i < appearedIn.size(); i++)
        {
            System.out.println(i+1 + ". " + appearedIn.get(i).getTitle());
        }
        System.out.print("Which movie do you want to learn more about: ");
        String movieSearch = scanner.nextLine();
        int search = Integer.parseInt(movieSearch);
        displayMovieInfo(appearedIn.get(search-1));
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();

    }

    private void searchKeywords()
    {
        System.out.println("Enter a keyword search term: ");
        String keySearch = scanner.nextLine();
        keySearch = keySearch.toLowerCase();
        ArrayList <Movie> search = new ArrayList <Movie>();
        for (int i = 0; i < movies.size(); i++)
        {
            String keyword = movies.get(i).getKeywords();
            keyword = keyword.toLowerCase();
            if (keyword.indexOf(keySearch) != -1)
            {
                search.add(movies.get(i));
            }
        }
        sortResults(search);
        for (int i = 0; i < search.size(); i++)
        {
            String title = search.get(i).getTitle();

            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();

    }

    private void listGenres() {
        ArrayList<String> genres = new ArrayList<String>();
        for (int i = 0; i < movies.size(); i++) {
            String[] tempGenre = movies.get(i).getGenres().split("\\|");

            for (int j = 0; j < tempGenre.length; j++) {
                if (!genres.contains(tempGenre[j]))
                    genres.add(tempGenre[j]);
            }
        }
        Collections.sort(genres);
        for (int i = 0; i < genres.size(); i++)
        {
            System.out.println(i+1 + ". " + genres.get(i));
        }
        System.out.print("Choose a genre: ");
        String searchStr = scanner.nextLine();
        int searchNum = Integer.parseInt(searchStr);
        String userGenre = genres.get(searchNum-1);
        ArrayList <Movie> genreMovies = new ArrayList<Movie>();
        for (Movie film: movies)
        {
            if (film.getGenres().contains(userGenre))
                genreMovies.add(film);
        }
        sortResults(genreMovies);
        for (int i = 0; i < genreMovies.size(); i++)
        {
            System.out.println(i+1 + ". " + genreMovies.get(i));
        }
        System.out.print("Choose a movie: ");
        String movieSearch = scanner.nextLine();
        int num = Integer.parseInt(movieSearch);
        displayMovieInfo(genreMovies.get(num-1));
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRated()
    {

    }

    private void listHighestRevenue()
    {

    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}