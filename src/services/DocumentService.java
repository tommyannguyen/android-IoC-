package services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import com.datalayer.CategoryRepository;
import com.datalayer.DocumentRepository;

import webtasks.CategoryTask;
import webtasks.DocumentTask;
import webtasks.HeaderTask;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;
import models.Document;
import models.IBaseResult;
import models.Pouche;
import models.Category;
import interfaces.*;

public class DocumentService implements IDocumentService {

	private final ConnectivityManager connectivityManager ;
	private final Context context;
	private final String userName="";
	private final String password="";
	private final CategoryRepository categoryRepository;
	private final DocumentRepository documentRepository;
	private static boolean isLoadOnWeb = false;
	
	@Inject
	public DocumentService(Provider<Context>context,Provider<ConnectivityManager>connectivityManager,CategoryRepository categoryRepository ,
			DocumentRepository documentRepository) {
		this.connectivityManager=connectivityManager.get();
		this.context=context.get();
		this.categoryRepository= categoryRepository;
		this.documentRepository =documentRepository;
	}

	@Override
	public Boolean IsNetworkAvaiable()
	{
		if (connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isAvailable()
                && connectivityManager.getActiveNetworkInfo().isConnected()) {

            return true;
		}
		return false;
	}
	@Override 
	public void SetLoadOnWeb(boolean value)
	{
		isLoadOnWeb = value;
	}
	@Override
	public void GetCategories(final IBaseResult<List<Category>> resultDocuments) {
		if(IsNetworkAvaiable() && isLoadOnWeb)
		{
			CategoryTask headertask = new CategoryTask("logincode", "password", new IBaseResult<List<Category>>() {
			@Override
			public void onSuccess(List<Category> result) {
				UpdateCategories(result);
				resultDocuments.onSuccess(GetLocalCategories());
			}
			
			@Override
			public void onError(String error) {
				resultDocuments.onSuccess(GetLocalCategories());
			}
			}) ;
			
			headertask.exec();
		}
		else
		{
			resultDocuments.onSuccess(GetLocalCategories());
		}
	}
	@Override
	public List<Document> GetDocumentsByCategoryId(int categoryId)
	{
		return documentRepository.getAll(categoryId);
	}
	@Override
	public Document GetDocument(int id)
	{
		return documentRepository.get(id);
	}
	private void UpdateCategories(List<Category> categories)
	{
		for(Category category: categories)
		{
			Category dbCategory = categoryRepository.get(category.Id);
			if(dbCategory == null)
			{
				categoryRepository.insert(category);
			}
			else
			{
				if(dbCategory.UpdatedDate.getTime() != category.UpdatedDate.getTime())
				{
					categoryRepository.update(category);
				}
			}
			UpdateDocuments(category.Documents);
		}
		
		List<Category> dbcategories = GetLocalCategories();
		List<Integer> dbDeletedcategories = new ArrayList<Integer>();
		for(Category dbcategory: dbcategories)
		{
			boolean isDelete= true;
			for(Category category: categories)
			{
				if(dbcategory.Id == category.Id)
				{
					isDelete=false;
				}
			}
			if(isDelete)
			{
				dbDeletedcategories.add(dbcategory.Id);
			}
		}
		for(Integer i : dbDeletedcategories)
		{
			categoryRepository.delete(i);
		}
	}
	private List<Category> GetLocalCategories()
	{
		return categoryRepository.getAll();
	}
	private void UpdateDocument(final Document document ,final Boolean isNew)
	{
		DocumentTask doctask=new DocumentTask("logincode", "password",document.Id, new IBaseResult<Document>() {
			@Override
			public void onSuccess(Document result) {
				result.CategoryId = document.CategoryId;
				if(isNew)
				{
					documentRepository.insert(result);
				}
				else
				{
					documentRepository.update(result);
				}
			}
			
			@Override
			public void onError(String error) {
				
			}
			}) ;
			
		doctask.exec();
	}
	private void UpdateDocuments(List<Document> documents)
	{
		for (final Document document : documents) {
			Document dbDocument = documentRepository.get(document.Id);
			
			if(dbDocument == null)
			{
				UpdateDocument(document,true);
			}
			else
			{
				if(dbDocument.UpdatedDate.getTime() != document.UpdatedDate.getTime())
				{
					UpdateDocument(document,false);
				}
			}
		}
	}
}
