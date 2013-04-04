package edu.ouhk.student.cubescape.util;

import java.util.Iterator;

public class StringArrayIterator implements Iterator<String> {
	private String[] values;
	private boolean isCircular = false;
	private int index;
	
	public StringArrayIterator(String[] values) {
		this.values = values;
		this.index = 0;
	}
	
	public StringArrayIterator(String[] values, boolean isCircular) {
		this(values);
		this.isCircular = isCircular;
	}

	@Override
	public boolean hasNext() {
		return isCircular?true:index<values.length;
	}

	@Override
	public String next() {
		index %= values.length;
		return values[index++];
	}

	@Deprecated
	@Override
	public void remove() {}
}
