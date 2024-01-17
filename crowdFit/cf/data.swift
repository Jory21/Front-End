//
//  data.swift
//  addexc
//
//  Created by Foundation 46 on 12/05/1445 AH.
//

import Foundation
import SwiftData

@Model
class data : Identifiable {
    var Excname: String
    var workname : String
    var numofsets : Int
    var numofreps : Int
    var day : String
    
    
   
    
    init(Excname:String ,workname:String ,numofsets:Int,numofreps: Int, day:String ){
        self.Excname = Excname
        self.workname = workname
        self.numofsets = numofsets
        self.numofreps = numofreps
        self.day = day
        
    }
    
    
    
}
    
    
    


