package com.technion.coolie;

import com.technion.coolie.skeleton.CoolieModule;;

public class CoolieModuleManager {
	
	/*
	 * Gets the CoolieModule of a class.
	 * Typical usage:  com.technion.coolie.CoolieModuleManager.getMyModule(this.getClass())
	 */
	public static CoolieModule getMyModule(Class<?> c){
		
		for(CoolieModule module: CoolieModule.values()){
			
			if(module.getPackage().equals(c.getPackage()))
				return module;
			
		}
		
		return null;
	}
}
