package com.trainingSpss;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import models.Document;
import interfaces.IDocumentService;
import roboguice.RoboGuice;
import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CategoryFragment extends RoboFragment  {
	@Inject
	IDocumentService documentService;
	
	@InjectView(R.id.listDocuments) ListView listDocuments;
	
	public static final int HOME_CATEGORY_ID = 0;
	private final int categoryId;
	private Context context;
	private  List<Document> documents;
	public CategoryFragment(int categoryId){
		this.categoryId = categoryId;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		context = getActivity().getApplicationContext();
        return inflater.inflate(R.layout.fragment_category, container, false);
    }
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
	    super.onViewCreated(view, savedInstanceState);
	    List<String> documentNames = new ArrayList<String>();
	    if(categoryId == HOME_CATEGORY_ID)
        {
        	
        }
        else
        {
        	documents = documentService.GetDocumentsByCategoryId(categoryId);
        	
        	for(Document document : documents)
        	{
        		documentNames.add(document.Title);
        	}
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1, documentNames){
        	@Override
        	public View getView(int position, View convertView, ViewGroup parent) {
        	    View view = super.getView(position, convertView, parent);
        	    TextView text = (TextView) view.findViewById(android.R.id.text1);
        	    text.setTextColor(Color.BLACK);
        	    return view;
        	  }
        	};
        listDocuments.setAdapter(adapter);
        listDocuments.setOnItemClickListener(new OnItemClickListener() {
			
        	 @Override
        	    public void onItemClick(AdapterView<?> parent, View view,
        	            int position, long id) {
        		 Document document = documents.get(position);
        		 Intent intent = new Intent(context, DocumentActivity.class);
        		 intent.putExtra(DocumentActivity.HOME_DOCUMENT_TOKEN,document.Id );
                 startActivity(intent);
        	    }
		});
	}
}
