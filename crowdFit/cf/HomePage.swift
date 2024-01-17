//
//  HomePage.swift
//  MashaelApp
//
//  Created by Foundation 43 on 13/05/1445 AH.
//

import SwiftUI
import SwiftData

//enum Tab: String, CaseIterable{
//    case bookmark
//    case calendar
//}
struct HomePage: View {

    @State private var sheets = false
    @State private var tab = false
    @State private var selection: String = "Scheld"
    enum Day: String, CaseIterable {
        case Sun, Mon, Tue, Wed, Thu, Fri, Sat
    }
    @State private var SDay: Day = .Mon
    @Query(sort: \data.day) var workout :[data]
    @Environment (\.modelContext) private var context
  
    var body: some View {
        VStack{
            VStack(alignment: .leading ,spacing: 5)
            {
                
                VStack{
                    
                    HStack{
                        Text("Hello...")
                            .font(.largeTitle)
                            .fontWeight(.semibold)
                        Spacer()
                        Image(systemName:"person.crop.circle")
                            .resizable()
                            .aspectRatio(contentMode: .fill)
                            .frame(width: 45 ,height: 45)
                            .clipShape(Circle())
                            .foregroundColor(.co)
                        
                        
                    }.padding()
                    ZStack{
                        RoundedRectangle(cornerRadius: 15)
                            .stroke(.gray)
                            .frame(width: 330 ,height: 50)
                        HStack{
                            
                            ForEach(Day.allCases, id: \.self){ day in Button(action: {
                                SDay = day
                                
                            }){
                                Text(day.rawValue).font(.title2)
                                    .foregroundColor(.primary)
                            }
                            .background(
                                Capsule()
                                    .fill(SDay == day ? .shado : Color(uiColor: .white))
                            )
                            .clipShape(Capsule())
                                
                            }
                        }
                        
                    }
                }         .padding()
                
                
                
                HStack{
                    Text("Today's Workout...")
                        .font(.title3)
                        .frame(width: 350,height: 15,alignment: .leading)
                    
                }
                
                Text("add exercises")
                    .font(.caption2)
                    .foregroundColor(Color.gray)
                    .frame(width: 350,height: 15,alignment: .leading)
                
                Divider()
                
                Button(action: {
                    sheets.toggle()
                    
                } ,label: {
                    ZStack
                    { Image(systemName:"plus")
                            .resizable()
                            .aspectRatio(contentMode: .fill)
                            .frame(width: 30 ,height: 45)
                            .clipShape(Circle())
                            .foregroundColor(.co)
                        VStack(alignment: .center ,spacing: 5){
                            RoundedRectangle(cornerRadius: 20)
                                .stroke(.co)
                                .frame(width: 320 ,height: 80)
                                .padding()
                            
                        }
                    }
                    
                })//label
                
                NavigationStack{// list of excrisce
                    
                    List {
                        ForEach (workout) { workout in
                            ZStack{
                                
                                
                                RoundedRectangle(cornerRadius: 20)
                                    .stroke(.co)
                                    .frame(width: 320 ,height: 80)
                                    .padding()
                                
                                
                                //   Text(workout.workname)
                                HStack(alignment: .center, spacing: 60) {
                                    
                                    
                                    Text(workout.Excname)
                                    HStack(alignment: .center, spacing:
                                            40){
                                        VStack{
                                            Text("Set")
                                            Text("\(workout.numofsets)")
                                        }// vstack
                                        VStack{
                                            Text("Rep")
                                            Text("\(workout.numofreps)")
                                            
                                        }//vstack
                                       
                                                    ZStack
                                                    { Image(systemName:"bookmark")
                                                            .resizable()
                                                            .aspectRatio(contentMode: .fill)
                                                            .frame(width: 10 ,height: 20)
                                                            .foregroundColor(.co)
                                                        
                                                    }
                                                    
                                            }
                                            
                                        
                                    }
                                             
                            }//zstack
                            
                        }.onDelete(perform: { indexes in
                            for index in indexes {
                                deletewoeks(workout[index])
                            }
                        })
                        
                    }.listStyle(.plain)
                }
                
                .sheet(isPresented: $sheets) {
                    sheetview(sheets: $sheets).presentationDetents([.medium])
                }
                .padding()
                
                
            } }//VS
        
        
        
    }//body
    func deletewoeks (_ workout : data){
        context.delete(workout)
    }
}//view

#Preview {
    HomePage()
}
