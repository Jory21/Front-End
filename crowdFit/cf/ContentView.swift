//
//  ContentView.swift
//  MashaelApp
//
//  Created by Foundation 43 on 08/05/1445 AH.
//

import SwiftUI
struct ContentView: View {
    // @State private var selectedTab: Tab = .calendar
    @State private var selection: String = "Scheld"
    @State private var tab = false
    
    
    var body: some View {
        NavigationStack{
            
            TabView( ) {
                HomePage()
                    .tabItem{
                        Image(systemName: "calendar")
                        Text("Scheldules")
                        
                    }
                
                
                SavedView()
                    .tabItem{
                        Image(systemName: "bookmark")
                        Text("Saved")
                    }
                
                Color.white
                    .tabItem{
                        Image(systemName: "person")
                        
                        Text("ProFile")
                        
                    }
            }.accentColor(Color.co)
                .padding()
            
            
        }
        
        
        
        
    }//body
    
    
}
//struct ContentView: View

#Preview {
    ContentView()
}


