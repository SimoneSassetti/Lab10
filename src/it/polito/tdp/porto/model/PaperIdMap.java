package it.polito.tdp.porto.model;

import java.util.HashMap;
import java.util.Map;

public class PaperIdMap {
	private Map<Integer,Paper> map;
	
	public PaperIdMap(){
		map=new HashMap<>();
	}
	public Paper get(int int1) {
		return map.get(int1);
	}

	public Paper put(Paper au) {
		Paper old=map.get(au.getEprintid());
		if(old==null){
			map.put(au.getEprintid(), au) ;
			return au ;
		}else{
			return old;
		}
	}

}
