package models;
import java.util.*;

public class Category {
	public Category()
	{
		Documents = new ArrayList<Document>();
		CountDocuments=0;
	}
	public int Id;
	public String Name;
	public Date UpdatedDate;
	public List<Document> Documents;
	public int CountDocuments;
}
