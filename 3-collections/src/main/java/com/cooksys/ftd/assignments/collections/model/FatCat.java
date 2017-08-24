package com.cooksys.ftd.assignments.collections.model;

import java.util.HashSet;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class FatCat implements Capitalist {

	private String name;
	private int salary;
	private FatCat owner;
	private HashSet<Capitalist> children;
	
    public FatCat(String name, int salary) {
    	this.name = name;
    	this.salary = salary;
    	this.owner = null;
    	children = new HashSet<Capitalist>();
    }

    public FatCat(String name, int salary, FatCat owner) {
    	this.name = name;
    	this.salary = salary;
    	this.owner = owner;
    	children = new HashSet<Capitalist>();
    }

    /**
     * @return the name of the capitalist
     */
    @Override
    public String getName() {
        return name;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + salary;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FatCat other = (FatCat) obj;
//		if (children == null) {
//			if (other.children != null)
//				return false;
//		} else if (!children.equals(other.children))
//			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (salary != other.salary)
			return false;
		return true;
	}

	/**
     * @return the salary of the capitalist, in dollars
     */
    @Override
    public int getSalary() {
        return salary;
    }

    /**
     * @return true if this element has a parent, or false otherwise
     */
    @Override
    public boolean hasParent() {
        return owner != null;
    }

    /**
     * @return the parent of this element, or null if this represents the top of a hierarchy
     */
    @Override
    public FatCat getParent() {
    	return owner;
    }
    
    public boolean addChild(Capitalist cap)
    {
    	return children.add(cap);
    }
    
    public boolean hasChildren()
    {
    	return children.size() > 0;
    }
    
    public HashSet<Capitalist> getChildren()
    {
    	return children;
    }
}
