//
//  ContentView.swift
//  addexc
//
//  Created by Foundation 46 on 06/05/1445 AH.
//

import SwiftUI
import SwiftData

struct sheetview: View {
 
 @Environment (\.modelContext) private var Context
 @Environment (\.dismiss) private var dismiss
 @Query(sort: \data.day) var workout : [data]
 enum Day: String, CaseIterable {
  case Sun, Mon, Tue, Wed, Thu, Fri, Sat
 }
 @Binding var sheets : Bool
 @State private var numofsets: Int = 1
 @State private var numofreps: Int = 1
 @State private var SDay: Day = .Mon
 @State private var workname : String = ""
 @State private var excname : String = ""
 
   
 var body: some View {
  NavigationView{
   VStack{
    HStack {
     Text("Workout Name").bold()
     Spacer()
     TextField("EX: legs", text: $workname ).textFieldStyle(.roundedBorder)
    }
    
    HStack{
     Text("Day").bold()
     Spacer()
     ForEach(Day.allCases, id: \.self) { day in
      Button(action: {
    SDay = day
      }) {
    Text(day.rawValue).foregroundColor(Color.black)
      }
      .background(
    Capsule()
        .fill(SDay == day ? .shado :Color(UIColor.white))     )
      .clipShape(Capsule())
      
     }
     
     
    }
    
    
    Divider()
    HStack {
     Text("Exercise Name").bold()
     TextField("EX: Narrow Stance Leg Press", text: $excname ).textFieldStyle(.roundedBorder)
    }
    ZStack{
     
     HStack {
      Spacer()
      Picker("Number of Sets", selection: $numofsets){
    ForEach(1...10, id: \.self) { number in
        Text("\(number)")
     
    }
      }.frame(width: 102.0, height: 25.0)
     }
     HStack {
      
      Text("Number of sets:").bold()
      Spacer()
      RoundedRectangle(cornerRadius: 13).stroke(.gray).frame(width: 133,height: 25)
     }
    }
    
    ZStack{
     
     HStack {
      Spacer()
      Picker("Number of reps", selection: $numofreps){
    ForEach(1...20, id: \.self) { number in
     Text("\(number)")
     
    }
      }.frame(width: 102.0, height: 25.0)
     }
     HStack {
      
      Text("Number of reps:").bold()
      Spacer()
      RoundedRectangle(cornerRadius: 13).stroke(.gray).frame(width: 133,height: 25)
     }
    }
    
    Divider()
    Button(action: {
    works()
     try! Context.save()
     dismiss()
   
    }
    )   {
     ZStack{
      Capsule()
    .frame(width: 130, height: 25.0)
    .cornerRadius(50)
      //  .background(Color.colorsy)
    .padding()
      
      Text("done")
    .font(.body)
    .foregroundColor(Color.white)
     }}
   }
  
   .navigationBarItems(trailing: Button(action:{ sheets.toggle()}
, label:{
    Text("cancel")
    
   }))
  }
   .padding()
 }
 func works() {
  let workout = data(Excname: excname, workname: workname, numofsets: numofsets, numofreps: numofreps, day: SDay.rawValue)
   Context.insert(workout)
 }
  }
 
 
  

  
// #Preview {
//  sheetview()
// }


