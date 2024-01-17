////
////  CustomTabBar.swift
////  MashaelApp
////
////  Created by Foundation 43 on 12/05/1445 AH.
////
//
//import SwiftUI
//
//enum Taab: String, CaseIterable{
//    case calendar
//    case bookmark
//    case person
//    
//}
//
//struct CustomTabBar : View {
////    @Binding var selectedTab: Taab
////    private var fillImage: String{
////        selectedTab.rawValue + ".fill"
////    }
////    
//    
//
//
//    
//    
//    var body: some View {
//        VStack{
//            HStack{
//                ForEach(Tab.allCases, id: \.rawValue) {tab in
//                    Spacer()
//                    Image(systemName: selectedTab == tab ? fillImage : tab.rawValue)
//                        .scaleEffect(selectedTab == tab ? 1.25 : 1.0)
//                        .foregroundColor(selectedTab == tab ? .co : .gray)
//                        .font(.system(size: 22))
//                    Spacer()
//                        
//                       
//                }
//                
//            }//HS
//
//                .frame(width: nil ,height: 60 )
//                .background(.thinMaterial)
//                .cornerRadius(10)
//                .padding()
//                
//          
//        }//VS
//    }//body
//
//}//view
//
//
//struct CustomTabBar_Previews: PreviewProvider{
//    static var previews: some View{
//        CustomTabBar(selectedTab: .constant(.calendar) )
//    }
//}
//
////#Preview {
////    CustomTabBar(selectedTab: .constant(.calendar))
////}
