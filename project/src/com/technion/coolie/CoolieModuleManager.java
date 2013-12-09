package com.technion.coolie;

import com.technion.coolie.skeleton.CoolieModule;;

public class CoolieModuleManager {
	CoolieModule getMyModule(Class<?> c){
		
		for(CoolieModule module: CoolieModule.values()){
			
			if(module.getPackage().equals(c.getPackage()))
				return module;
			
		}
		
		return null;
	}
}
