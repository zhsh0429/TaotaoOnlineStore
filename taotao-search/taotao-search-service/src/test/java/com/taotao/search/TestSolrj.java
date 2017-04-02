package com.taotao.search;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class TestSolrj {

	@Test
	public void addDocument() throws Exception{
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.175:8180/solr/collection1");
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "123");
		document.addField("item_title", "test_item_title");
		document.addField("item_sell_point", "test_item_sell_point");
		document.addField("item_price", "9999");
		document.addField("item_image", "test_item_image");
		document.addField("item_category_name", "test_item_category_name");
		solrServer.add(document);
		solrServer.commit();
	}
	
	@Test
	public void deleteDocument() throws Exception{
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.175:8180/solr/collection1");
		solrServer.deleteById("123");
		solrServer.commit();
	}
}
