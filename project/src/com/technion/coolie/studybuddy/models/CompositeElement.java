package com.technion.coolie.studybuddy.models;

import com.technion.coolie.studybuddy.data.CompositeVisitor;

public interface CompositeElement
{
	public void accept(CompositeVisitor cv);
}
