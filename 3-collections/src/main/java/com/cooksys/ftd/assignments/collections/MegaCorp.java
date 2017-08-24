package com.cooksys.ftd.assignments.collections;

import com.cooksys.ftd.assignments.collections.hierarchy.Hierarchy;
import com.cooksys.ftd.assignments.collections.model.Capitalist;
import com.cooksys.ftd.assignments.collections.model.FatCat;
import com.cooksys.ftd.assignments.collections.model.WageSlave;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

public class MegaCorp implements Hierarchy<Capitalist, FatCat> {
	
	private HashMap<FatCat,HashSet<Capitalist>> corpStructure = new HashMap<FatCat,HashSet<Capitalist>>();
	private HashSet<Capitalist> capSet = new HashSet<Capitalist>();
	
    /**
     * Adds a given element to the hierarchy.
     * <p>
     * If the given element is already present in the hierarchy,
     * do not add it and return false
     * <p>
     * If the given element has a parent and the parent is not part of the hierarchy,
     * add the parent and then add the given element
     * <p>
     * If the given element has no parent but is a Parent itself,
     * add it to the hierarchy
     * <p>
     * If the given element has no parent and is not a Parent itself,
     * do not add it and return false
     *
     * @param capitalist the element to add to the hierarchy
     * @return true if the element was added successfully, false otherwise
     */
    @Override
    public boolean add(Capitalist capitalist) {
        
    	if(capitalist != null)
    	{	
    		if(capSet.contains(capitalist))
    			return false;
    		if(capitalist.hasParent())
    		{
    			capitalist.getParent().addChild(capitalist);
    			add(capitalist.getParent());
    			return capSet.add(capitalist);
    		}
    		else
    		{
    			if(capitalist instanceof WageSlave)
    				return false;
    			else 
    			{
    				return capSet.add(capitalist);
    			}
    		}
    	}
    	return false;
    }

    /**
     * @param capitalist the element to search for
     * @return true if the element has been added to the hierarchy, false otherwise
     */
    @Override
    public boolean has(Capitalist capitalist) {
    	return capSet.contains(capitalist);
    }

    /**
     * @return all elements in the hierarchy,
     * or an empty set if no elements have been added to the hierarchy
     */
    @Override
    public Set<Capitalist> getElements() {
    	HashSet<Capitalist> tempSet = new HashSet(capSet);
        return tempSet;
    }

    /**
     * @return all parent elements in the hierarchy,
     * or an empty set if no parents have been added to the hierarchy
     */
    @Override
    public Set<FatCat> getParents() {
    	HashSet<FatCat> tempSet = new HashSet<FatCat>();;
    	for(Capitalist c : capSet)
    	{
    		if(c instanceof FatCat)
    		{
    			tempSet.add((FatCat)c);
    		}
//    		if(!(c instanceof WageSlave))
//    		{
//    			if(((FatCat)c).hasChildren())
//    			{
//    				childrenExist = true;
//    				tempSet.add((FatCat)c);
//    			}
//    		}   	
    	}
    	
//    	if(!childrenExist)
//    	{
//    		//System.out.println("THERE WERE NO CHILDREN");
//    		for(Capitalist c : capSet)
//    		{
//    			//if(c instanceof FatCat)
//    				tempSet.add((FatCat)c);
//    		}
//    	}
    	
    	
    	return tempSet;
    }

    /**
     * @param fatCat the parent whose children need to be returned
     * @return all elements in the hierarchy that have the given parent as a direct parent,
     * or an empty set if the parent is not present in the hierarchy or if there are no children
     * for the given parent
     */
    @Override
    public Set<Capitalist> getChildren(FatCat fatCat) {

    	FatCat temp = new FatCat(new String(fatCat.getName()),new Integer(fatCat.getSalary()),(fatCat.getParent()));

    	HashSet<Capitalist> tempSet = new HashSet<Capitalist>(); 
    	for(Capitalist c : fatCat.getChildren())
    	{
    		tempSet.add(c);
    	}
    	return tempSet;
    }

    /**
     * @return a map in which the keys represent the parent elements in the hierarchy,
     * and the each value is a set of the direct children of the associate parent, or an
     * empty map if the hierarchy is empty.
     */
    @Override
    public Map<FatCat, Set<Capitalist>> getHierarchy() {

    	HashMap<FatCat,Set<Capitalist>> tempMap = new HashMap<FatCat,Set<Capitalist>>();
    	HashSet<Capitalist> tempSet = new HashSet(capSet);
    	
    	FatCat tempCat;

    	for(Capitalist c : capSet)
    	{
    		if(!(c instanceof WageSlave))
    		{
    			//if(!((FatCat)c).hasParent())
    			//{
    				//tempSet.remove(c);
    				tempMap.put((FatCat)c, new HashSet<Capitalist>());		
    				for(Capitalist e : getChildren((FatCat)c))
    					tempMap.get(c).add(e);
    			//}
    		}
    	}
//    	for(Capitalist c : tempSet)
//    	{
//    		if(c.hasParent())
//    		{
//    			for(Capitalist d : tempMap.keySet()){
//    				if(d.equals( c.getParent()))
//    					tempMap.get(d).add(c);
//    			}
//    		}
//    		
//    	}
    	
    	return tempMap;
    }

    /**
     * @param capitalist
     * @return the parent chain of the given element, starting with its direct parent,
     * then its parent's parent, etc, or an empty list if the given element has no parent
     * or if its parent is not in the hierarchy
     */
    @Override
    public List<FatCat> getParentChain(Capitalist capitalist) {

    	ArrayList<FatCat> pChain = new ArrayList<FatCat>();
    	
    	if(capitalist != null)
    	{
    		Capitalist tempCap = capitalist;
    	
    	
    		while(tempCap.hasParent())
    		{
    			if(inHierarchy(tempCap.getParent()))
    			{
    				pChain.add(tempCap.getParent());
    				tempCap = tempCap.getParent();
    			}
    			else break;
    		}
    	}
    	return pChain;
    }
    
    private boolean inHierarchy(Capitalist capitalist)
    {
    	Map<FatCat, Set<Capitalist>> hierarchy = getHierarchy();
    	
    	if(hierarchy.keySet().contains(capitalist))
    		return true;
    	else
    	{
    		for(Capitalist c : hierarchy.keySet())
    		{
    			if(hierarchy.get(c).contains(capitalist))
    				return true;
    		}
    	}
    	
    	return false;
    }
    
}
