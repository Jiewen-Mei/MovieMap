/**
 * 
 */
package MovieGenreMap;

/**
 * @author Jiewen
 *
 */
public class Movie {
	private String tittle;
	private int releaseYear;
	Movie next;
	public Movie () {
		tittle="";
		releaseYear=0;
		next=null;
	}
	public Movie(String tittle, int releaseYear) {
		this.tittle=tittle;
		this.releaseYear=releaseYear;
		next=null;
	}
	public int getReleaseYear() {
		return releaseYear;
	}
	public String getTittle() {
		return tittle;
	}
}
