package com.tranxactor.pagination1.Service;

import java.util.List;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import net.minidev.json.JSONArray;

public class JsonPathFinder {

	public static void main(String[] args) {

		 String json = "[{\"name\":\"Estonia\",\"topLevelDomain\":[\".ee\"],\"alpha2Code\":\"EE\",\"alpha3Code\":\"EST\",\"callingCodes\":[\"372\"],\"capital\":\"Tallinn\",\"altSpellings\":[\"EE\",\"Eesti\",\"Republic of Estonia\",\"Eesti Vabariik\"],\"region\":\"Europe\",\"subregion\":\"Northern Europe\",\"population\":1315944,\"latlng\":[59.0,26.0],\"demonym\":\"Estonian\",\"area\":45227.0,\"gini\":36.0,\"timezones\":[\"UTC+02:00\"],\"borders\":[\"LVA\",\"RUS\"],\"nativeName\":\"Eesti\",\"numericCode\":\"233\",\"currencies\":[{\"code\":\"EUR\",\"name\":\"Euro\",\"symbol\":\"€\"}],\"languages\":[{\"iso639_1\":\"et\",\"iso639_2\":\"est\",\"name\":\"Estonian\",\"nativeName\":\"eesti\"}],\"translations\":{\"de\":\"Estland\",\"es\":\"Estonia\",\"fr\":\"Estonie\",\"ja\":\"エストニア\",\"it\":\"Estonia\",\"br\":\"Estônia\",\"pt\":\"Estónia\",\"nl\":\"Estland\",\"hr\":\"Estonija\",\"fa\":\"استونی\"},\"flag\":\"https://restcountries.eu/data/est.svg\",\"regionalBlocs\":[{\"acronym\":\"EU\",\"name\":\"European Union\",\"otherAcronyms\":[],\"otherNames\":[]}],\"cioc\":\"EST\"}]";
		  String json2 =   "{\r\n" + 
		  		"    \"employees\": [\r\n" + 
		  		"        {\r\n" + 
		  		"            \"firstName\": \"Peter\",\r\n" + 
		  		"            \"lastName\": \"Jones\"\r\n" + 
		  		"        }\r\n" + 
		  		"    ]\r\n" + 
		  		"}";
		  
		  String json3 = "{\"house\"[\"persons\"[\"Father\":Sreedhar]]}"; 
		 String jsonPath = "$.[*].name";  
 	    DocumentContext jsonContext = JsonPath.parse(json);
 	    List<String> result = jsonContext.read(jsonPath);
 	   System.out.println("name :: "+result.get(0));
 	    
 	    result = jsonContext.read("$.[*].capital"); //To get Captial
 	   System.out.println("Capital :: "+result.get(0));   
		
 	  JSONArray  errorArray = jsonContext.read("$.[*].callingCodes");
 	 errorArray = jsonContext.read("$.[*].callingCodes");
 	  System.out.println("callingCodes :: "+errorArray.get(0));  
 	  
		
 	 String jsonPathnew = "$..firstName";
 	DocumentContext jsonContext2 = JsonPath.parse(json3);
 	
 	 JSONArray  errorArray2 = jsonContext2.read(jsonPathnew);
 	 
 	 
 	 //System.out.println("firstname :: " + result3 );
	}

}