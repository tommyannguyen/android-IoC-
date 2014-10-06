package interfaces;

import java.util.*;
import models.*;

public interface IDocumentService {
	Boolean IsNetworkAvaiable();
	void GetCategories(IBaseResult<List<Category>> result);
	List<Document> GetDocumentsByCategoryId(int categoryId);
	Document GetDocument(int id);
}
