//
//  Saved.swift
//  MashaelApp
//
//  Created by Foundation 43 on 13/05/1445 AH.
//

import SwiftUI


struct SavedView: View {
    @State private var tab = false
    
    var body: some View {
        
        // Header
        VStack (alignment: .center){
            Text("Saved")
                .font(.title)
                .fontWeight(.bold)
            Text("Saved From Friends")
                .foregroundColor(.gray)
            Spacer()
            
            VStack{
                ZStack{
                    
                    
                    RoundedRectangle(cornerRadius: 20)
                        .stroke(.co)
                        .frame(width: 320 ,height: 80)
                        .padding()
                    
                    
                    HStack(alignment: .center, spacing: 90) {
                        
                        
                        Text("Legs")
                        HStack(alignment: .center, spacing:
                                40){
                            VStack{
                                Text("Set")
                                Text("3")
                            }// vstack
                            VStack{
                                Text("Rep")
                                Text("10")
                                
                            }//vstack
                            
                            Button(action: {
                                tab.toggle()
                                
                                
                            } ,label: {
                                ZStack
                                { Image(systemName:"bookmark.fill")
                                        .resizable()
                                        .aspectRatio(contentMode: .fill)
                                        .frame(width: 10 ,height: 20)
                                        .foregroundColor(.co)
                                    
                                }
                                
                            })//label
                            
                        }
                    }
                }}.offset(y:-500)
            
        } //vstack
        
    }//body
}//view


#Preview {
    SavedView()
}
