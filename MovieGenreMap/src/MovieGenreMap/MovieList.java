/**
 * 
 */
package MovieGenreMap;
import java.util.*;
/**
 * @author Jiewen
 *
 */
public class MovieList {
	Movie first;
	public MovieList() {
		first=null;
	}
	public boolean isEmpty() {
		return first==null;
	}
//insert Movie in descending order; the lattest releaseyear is the first;
	public void insert(String tittle, int releaseYear) {
		Movie curr=new Movie(tittle,releaseYear);
		Movie tmp=null;
		Movie current=first;
		if(first==null) {first=curr;}
		else {
			while(current!=null) {
				if(first.getReleaseYear()<curr.getReleaseYear()) {
					tmp=first;
					first=curr;
					curr.next=tmp;
				}
				current=current.next;
				}
		}
	}
//close insert()
	//total method is to count the whole MovieList;
	public long total() {
		long count=0;
		Movie curr=first;
		while(curr!=null) {
			count++;
			curr=curr.next;
			
		}
		return count;
	}
	
	//restrictcount method is to count the list with the filter (current year - 5) 
	public int restrictCount(int currentYear) {
		int count=0;
		Movie curr=first;
		
		while(curr!=null) {
			
			if(curr.getReleaseYear()>=(currentYear-5)) {
				count++;
			}
			curr=curr.next;
			}
		return count;
		}
	
	//countByYear is to create a 2d array for the Movie list; since the list is sorted in descending order already,
	//if the year is diferent than the last one, it will go to first column and there's a counter to count how many are the same year, 
	//and counters will go to second colume that assiciaoted with the right release year;
	public int[][] countByYear(){
		Set<Integer> estSize=new HashSet<Integer>(20);
		Movie curr=first;
		if(curr!=null) {
			estSize.add(first.getReleaseYear());
			curr=curr.next;
		}
		List<Integer> numbersList = new ArrayList<Integer>(estSize);
		Collections.sort(numbersList);
		int[][] data=new int[numbersList.size()][2];
		int i=0;
		for(Integer year: numbersList) {
			data[i][0]=year;
			int count=0;
			Movie nextMovie=first;
			if(nextMovie!=null) {
				if(nextMovie.getReleaseYear()==year) {
					count++;
				}
				nextMovie=nextMovie.next;
				}
			data[i][1]=count;
		}
		return data;
	}
	}
	