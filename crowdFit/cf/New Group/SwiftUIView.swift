//
//  SwiftUIView.swift
//  MashaelApp
//
//  Created by Foundation 43 on 09/05/1445 AH.
//
//
//import SwiftUI
//
//enum Tab: String, CaseIterable{
//    case calendar
//    case bookmark
//    case person
//    
//}
//
//struct SwiftUIView: View {
//    @Binding var selectedTab: Tab
//    private var fillImage: String{
//        selectedTab.rawValue + ".fill"
//    }
//    
//    
//    
//    
//    var body: some View {
//        Text("h")
//        VStack{
//            HStack{
//                ForEach(Tab.allCases, id: \.rawValue) {tab in
//                    Spacer()
//                    Image(systemName: selectedTab == tab ? fillImage : tab.rawValue)
//                        .scaleEffect(selectedTab == tab ? 1.25 :1.0)
//                        .foregroundColor(selectedTab == tab ? .co : .gray)
//                        .font(.system(size: 22))
//                        .onTapGesture{
//                            withAnimation(.easeIn(duration: 0.1))
//                            selectedTab = tab;
//                        }
//                        
//                        
//                            
//                       
//                }
//                
//                
//                .frame(width: nil ,height: 60 )
//                .background(.thinMaterial)
//                .cornerRadius(10)
//                .padding()
//                
//          
//            }//HS
//        }//VS
//    }//body
//    
//}//view
//    #Preview {
//        ContentView()
//    }
//
//    struct SwiftUIView_Previews: PreviewProvider{
//        static var previews: some View{
//            SwiftUIView(selectedTab: .constant(.calendar))
//        }
//    }
//    
//    
//    
//    
//    
//    























//import SwiftUI
//
//struct SwiftUIView: View {
//    @State private var selection: String = "Scheld"
//
//    var body: some View {
//
//        TabView{
//
//            TabView(selection: $selection ) {
//                Color.white
//                    .tabItem{
//                        Image(systemName: "calendar")
//                        Text("Scheldules")
//
//                    }
//                Color.white
//                    .tabItem{
//                        Image(systemName: "bookmark")
//
//                        Text("Saved")                            }
//                Color.white
//                    .tabItem{
//                        Image(systemName: "person")
//
//                        Text("ProFile")
//
//                    }
//            }.accentColor(Color.co)
//                .padding()
//        }
//
//        }
//    }
//
//#Preview {
//    ContentView()
//}








import SwiftUI

struct User: Identifiable {
    let id = UUID()
    let email: String
    let password: String
    let username: String
}

class UserData: ObservableObject {
    @Published var users: [User] = []
}

struct SignUpView: View {
    //   @State private var email: String = ""
    @State  var username: String = ""
    @State var email: String = ""
    @State private var password: String = ""
    @State private var showAlert: Bool = false
    
    @EnvironmentObject private var userData: UserData
    
    var body: some View {
        
        
        NavigationView {
            VStack(spacing: 20) {
                Text("LET'S START")
                    .font(.largeTitle)
                    .padding()
                VStack(alignment: .leading) {
                    Text("Username")
                        .padding(.leading)
                    HStack{
                        Image(systemName: "person")
                        TextField("Enter A Username", text: $username)
                        
                    } .padding()
                    
                        .background(RoundedRectangle(cornerRadius: 50).strokeBorder(Color(red: 0.011, green: 0.016, blue: 0.353)))
                        .padding(.horizontal, 9)
                    
                }
                
                VStack(alignment: .leading) {
                    Text("Email")
                        .padding(.leading)
                    HStack{
                        Image(systemName: "envelope")
                        TextField("Enter Your Email", text: $email)
                        
                    } .padding()
                        .background(RoundedRectangle(cornerRadius: 50).strokeBorder(Color(red: 0.011, green: 0.016, blue: 0.353)))
                        .padding(.horizontal, 9)
                    
                }
                
                VStack(alignment: .leading) {
                    Text("Password")
                        .padding(.leading)
                    HStack{
                        Image(systemName: "lock")
                        SecureField("Enter A Password", text: $password)
                        
                    }.padding()
                        .background(RoundedRectangle(cornerRadius: 50).strokeBorder(Color(red: 0.011, green: 0.016, blue: 0.353)))
                        .padding(.horizontal, 9)
                    
                }
                .padding(.bottom)
                
                
                
                
                ////
                //            Button("Sign Up") {
                //                let newUser = User(email: email, password: password, username: username)
                //                userData.users.append(newUser)
                //                showAlert = false
                //
                //                NavigationLink(destination: ProfileView(email: $email, isLoggedIn: .constant(false), username: username)) {
                //
                //                }
                //                //navigationlink
                //
                //                    //profiel view contains as binding
                //            }
                
                
                
                
                
                
                
                
                
                NavigationLink {
                    //
                    
                    ProfileView(email: $email, isLoggedIn: .constant(false), username: username)
                } label: {
                    Text("sign Up")
                    
                    
                    
                    
                }
                .foregroundColor(.white)
                .frame(height: 40)
                .frame(maxWidth:200)
                .background(Color(hue: 0.644, saturation: 0.861, brightness: 0.846))
                .cornerRadius(50)
                
                
                .padding()
                
                
                
                .padding(.horizontal, 90)
                .padding(.vertical, 8)
                .padding(.horizontal)
            }
            .alert(isPresented: $showAlert) {
                Alert(
                    title: Text("Invalid Input"),
                    message: Text("Please enter a valid email and password."),
                    dismissButton: .default(Text("OK"))
                )
            }
            .navigationTitle("")
        }
        
    }
}

struct LoginView: View {
    @State private var email: String
    @State private var password: String
    @State private var showAlert: Bool = false
    
    @EnvironmentObject private var userData: UserData
    @Binding var isLoggedIn: Bool
    
    var body: some View {
        VStack(spacing: 20) {
            Spacer()
            
            Image("logo")
                .resizable()
                .frame(width: 156, height: 100)
            Text("Log In")
                .font(.title)
            VStack (alignment:.leading){
                Text("Email")
                    .padding(.leading)
                HStack {
                    Image(systemName: "envelope")
                    TextField("Enter Your Email", text: $email)
                }
                .frame(maxWidth:330)
                
                .padding()
                .background(RoundedRectangle(cornerRadius: 50).strokeBorder(Color(red: 0.011, green: 0, blue: 0.353)))
            }
            
            VStack(alignment: .leading) {
                Text("Password")
                    .padding(.leading)
                HStack{
                    Image(systemName: "lock")
                    SecureField("Enter A Password", text: $password)
                    
                } .frame(maxWidth:330)
                
                    .padding()
                    .background(RoundedRectangle(cornerRadius: 50).strokeBorder(Color(red: 0.011, green: 0, blue: 0.353)))
            }
            .padding(.bottom)
            
            Button("Log In") {
                if userData.users.contains(where: { $0.email == email && $0.password == password }) {
                    isLoggedIn = true
                    showAlert = false
                } else {
                    showAlert = true
                }
            }
            .padding()
            .foregroundColor(.white)
            .frame(height: 40)
            .frame(maxWidth:200)
            .background(Color(hue: 0.644, saturation: 0.861, brightness: 0.846))
            .cornerRadius(50)
            Spacer()
        }
        .alert(isPresented: $showAlert) {
            Alert(
                title: Text("Invalid Credentials"),
                message: Text("Please check your email and password."),
                dismissButton: .default(Text("OK"))
            )
        }
        
    }
}

struct ProfileView: View {
    @Binding var email: String
    @Binding var isLoggedIn: Bool
    @State  var username: String
    
    var body: some View {
        
        
        VStack{
            
            VStack{
                
                Spacer()
                
                Image("logo")
                    .resizable()
                    .aspectRatio(contentMode: /*@START_MENU_TOKEN@*/.fill/*@END_MENU_TOKEN@*/)
                    .frame(width: 180, height: 100)
                    .padding(22)
                    .padding(.top,-50)
                Image(systemName: "camera.circle")
                    .font(.system(size: 30))
                    .foregroundColor(/*@START_MENU_TOKEN@*/.blue/*@END_MENU_TOKEN@*/)
                    .offset(x:60, y:-30)
                Spacer()
                
                Text((username))
                    .font(.title)
                    .bold()
                Spacer()
                
            }
            Spacer()
            List {
                Section(header: Text("Account Information")) {
                    Text("Email: \(email)")
                        .font(.body)
                        .padding()
                    Text("Username: \(username)")
                        .font(.body)
                        .padding()
                    Button(action: {
                        logout()
                    }) {
                        Text("Logout")
                            .foregroundColor(.black)
                            .font(.headline)
                            .padding()
                        // .background)
                            .cornerRadius(10)
                    }
                    .listRowBackground(Color.white)
                }
            }
            
            Spacer()
        }
    }
    
    func logout() {
        
        email = ""
        isLoggedIn = true
    }
}



//    struct ContentView: View {
//        @State  var isLoggedIn: Bool = false
//        @StateObject  var userData = UserData()
//        @State  var email: String
//        @State  var username: String
//        
//        var body: some View {
//            
//            ProfileView( email : $email, isLoggedIn: .constant(true) ,username: username)
////                          
//            }
//         
//           
//    }
//    
//    struct ContentView_Previews: PreviewProvider {
//        static var previews: some View {
//            ContentView(email: "", username: "")
//        }
//    }
