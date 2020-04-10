/**
 * 
 */
package MovieGenreMap;

import java.io.*;
import java.util.*;

import movieBst.Movie;



/**
 * @author Jiewen
 *
 */
public class MovieMap {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, MovieList> movieMap=new HashMap<>(36);
		readIn(new File("movies.csv"), movieMap);
		long avg=average(movieMap);
		System.out.println("Average: " + avg);
		int recentAvg=recentAvg(movieMap,2020);
		sortByAvg(movieMap,avg);
		sortByRestrictAvg(movieMap,recentAvg,2020);
		System.out.println("ended");
	}
	

	/*  readIn file can retrieve all genres and call put() method to add genres as keys for maps, and add Movie objects consists of movie titles to
 * the movieList as values for HashMap
 * 
 */
	public static void readIn(File onefile,  HashMap<String, MovieList> movieMap)throws Exception {
		Scanner sc = new Scanner(onefile,"UTF-8");
		sc.nextLine();
		String[] tmp0=new String[5];      //tmp0, tmp1 are used for split up the csv line into string literals
		String[] tmp1=new String[5];  
		String[] genres=new String[10];  
		int check=0,id=0,year=0;        //check is used for detect the strings which have more than one pair of ();
		String t="";
		ArrayList<String> exceptions=new ArrayList<>(20);
		while(sc.hasNext()) {
			String s=sc.nextLine();
			if(s.contains("no genres")) {exceptions.add(s);}
			else {
			check=s.indexOf("(");
			check=s.indexOf("(",check+1);
			if(s.contains("\"")||check>0) {          //this if block is for lines with "" or more than one ()
				int lastcomma=s.lastIndexOf(",");   //basically, strings literals are retrieved by tracking comma and () positions in the String;
				int firstcomma=s.indexOf(",");
				genres=s.substring(lastcomma).split("\\|");
				id=Integer.parseInt(s.substring(0,firstcomma));
				int lastP=s.lastIndexOf("(");
				year=Integer.parseInt(s.substring(lastP+1,lastcomma-2));
				t=s.substring(firstcomma+1,lastP);
				t=t.replace("\"","");
				t=t.trim();
			}
			else {                           //else block is for standard csv, it simply retrieves data using split();
			tmp0=s.split(",");
			id=Integer.parseInt(tmp0[0]);
			tmp1=tmp0[1].split("\\(");
			t=tmp1[0];
			t=t.trim();
			if(check>0) {
			year=Integer.parseInt(tmp1[1].replace(")","").trim());
			}
			else {year=0;}
			System.out.println("tmp0 " +tmp0[2]+ "\n ID: " + id);
			genres=tmp0[2].split("\\|");
			System.out.println("Genre: " + genres[0]);
			}
			put(t,year,genres,movieMap);
			}
		}
		sc.close();
			
	}//close readIn method
	
	
/*
 *  put() is to go over the genres string array for each genres for one movie, and add it to the map as key, and movies will add to this genre's 
 *  movielist in descending order(most recent release year to the oldest); if key is existed already, it will just add movies to movieList;
 */
	public static void put(String tittle, int releaseYear, String[] genres, HashMap<String, MovieList> movieMap) {
		for(int i=0;i<genres.length;i++) {
			genres[i]=genres[i].replace(",","");
			genres[i]=genres[i].trim();
			System.out.println("the current genres being added is :"+genres[i]);
			if(movieMap.containsKey(genres[i])){
				movieMap.get(genres[i]).insert(tittle, releaseYear);
			}
			else {
				MovieList movieList=new MovieList();
				movieList.insert(tittle,releaseYear);
				movieMap.put(genres[i],movieList);
			}
		}
	}
	
	// average is to calculate the total of movies each genres, and add them up and calculate the average;
	public static long average(HashMap<String, MovieList> movieMap) {
		Set<String> keys=movieMap.keySet();
		for(String g: keys) {System.out.println("key is "+g);}
		System.out.println("the size of genres: "+keys.size()); 
		
		long total=0;
		for(String key: keys) {
			total+=movieMap.get(key).total();
			System.out.println("TOTAL FOR EACH GENRE: "+total);
		}
		System.out.println("total from avergae:    "+total);
		System.out.println("total genres types: "+ keys.size());
		return total/keys.size();
	}
	
	// recenrAvg is to calculate the total movies in (2015-2020) of each genre and add them up, and calculate the average; 
	public static int recentAvg(HashMap<String, MovieList> movieMap, int currentYear) {
		Set<String> keys=movieMap.keySet();
		int total=0;
		for(String key: keys) {
			total+=movieMap.get(key).restrictCount(currentYear);
		}
		return total/keys.size();
	}
	
	
	//sortByavg is to use selection sort to sort genres in descending orders and split them into two groups based on average;
	public static void sortByAvg(HashMap<String, MovieList> movieMap, long average)throws IOException {
		PrintWriter pw=new PrintWriter("sortByAvg.txt");
		Set<String> keys=movieMap.keySet();
		String[] genres=new String[keys.size()];
		long[] size=new long[keys.size()];
		int i=0;
		for(String key:keys) {
			genres[i]=key;
			size[i]=movieMap.get(key).total();
			i++;
		}
		for (int j = 0; j < size.length-1; j++) { 
            int max = j; 
            for (int k = j+1; k < size.length; k++) { 
                if (size[k] > size[max]) { 
                    max=k;
                }
            }
           
            long temp = size[max]; 
            String tmp=genres[max];
            size[max] = size[j];
            genres[max]=genres[j];
            size[j] = temp; 
            genres[j]=tmp;
        }  
		pw.println("The average of the whole data is "+average);
		pw.print("genres that are above average:");
		int p=0;
		System.out.println(size[p]);
		while(p<size.length && size[p]>=average) {
				pw.println(genres[p]+"   "+size[p]);
				pw.println();
				System.out.println("counting :"+p);
				p++;
		}
		pw.println("genres that are below average: ");
		System.out.println("the current position is "+p);
		while(p<size.length && size[p]<average) {
				pw.println(genres[p]+"  "+size[p]);
				pw.println();
				p++;	
			}
		pw.close();
	}
	
	//sortByRestrictAvg is to use selection sort to sort genres in descending orders and split them into two groups based on average;
	//(only movies in 2015-2020 are counted);
	public static void sortByRestrictAvg(HashMap<String, MovieList> movieMap, int average, int currentYear)throws IOException {
		PrintWriter pw=new PrintWriter("sortByRestricAvg.txt");
		Set<String> keys=movieMap.keySet();
		String[] genres=new String[keys.size()];
		int[] size=new int[keys.size()];
		int i=0;
		for(String key:keys) {
			genres[i]=key;
			size[i]=movieMap.get(key).restrictCount(currentYear);
		}
		for (int j = 0; j < size.length-1; j++) { 
            int max = j; 
            for (int k = j+1; k < size.length; k++) { 
                if (size[k] > size[max]) { 
                    max=k;
                }
            }
           
            int temp = size[max]; 
            String tmp=genres[max];
            size[max] = size[j];
            genres[max]=genres[j];
            size[j] = temp; 
            genres[j]=tmp;
        }  
		pw.println("The average in recent 5 years is "+average);
		pw.print("Genres that are above average:");
		int p=0;
		while(p<size.length && size[p]>=average) {
				pw.println(genres[p]+"  "+size[p]);
				pw.println();
				p++;
		}
		pw.println("Genres that are below average: ");
		while(p<size.length && size[p]<average) {
				pw.println(genres[p]+"  "+size[p]);
				pw.println();
				p++;	
			}
		pw.close();
	}
	
	//trend is to print out data for each genre in each year;
	//since one movieList is for one genre, and they are sorted in descending already, so, it just needs to use first movie's release year as
	//filter and then go to next movie; if it's the same year, just simply add up the counter; once the release year is different, reset the filter and
	//counter; and repeat the process to go over movie Object in the list one by one;
	public static void trend(HashMap<String, MovieList> movieMap)throws IOException {
		Set<String> genres=  movieMap.keySet();
		PrintWriter op=new PrintWriter("EachGenreEachYear.txt");
		for(String type: genres) {
			op.println("Genre-----"+type);
			int[][] output=movieMap.get(type).countByYear();
			for(int i=0;i<output.length;i++) {
				op.println(output[i][0]+"\t"+output[i][1]);
			}
			op.println();
			op.println();
		}
		op.close();
	}
	
	
}//close class
		