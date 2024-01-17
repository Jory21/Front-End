//
//  saveView.swift
//  MashaelApp
//
//  Created by Foundation 43 on 15/05/1445 AH.
//

import SwiftUI

struct saveView: View {
    var body: some View {
        
        NavigationView{
            
            NavigationLink("saved"){
                VStack (alignment: .center) {
                    Text("Saved")
                        .font(.title)
                        .fontWeight(.bold)
                    Text("Saved From Friends")
                        .foregroundColor(.gray)
                    Spacer()
                }
                
                
                
                
            }
        }
    }
}
#Preview {
    saveView()
}
