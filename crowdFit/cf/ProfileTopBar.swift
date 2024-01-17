//import SwiftUI
//
//// ViewModel to manage user data
//class tUserViewModel: ObservableObject {
//    @Published var username: String = "John Doe"
//    @Published var email: String = "john.doe@example.com"
//    // Add more user-related properties as needed
//
//    func logout() {
//        // Implement logout functionality, e.g., clearing user data
//        username = ""
//        email = ""
//    }
//}
//
//struct ProfileTopBar: View {
//    @ObservedObject var userViewModel: UserViewModel
//
//    var body: some View {
//        Spacer()
//        NavigationStack {
//            List {
//                Text("Profile")
//                        .font(.title)
//                        .padding()
//                 Text("Username: \(userViewModel.username)")
//                Text("Email: \(userViewModel.email)")
//                
//            
//                    Button("Logout") {
//                        userViewModel.logout()
//                    }
//                    .padding()
//                    .foregroundColor(.red)
//                    .padding(.bottom)
//                    
//                    
//                
//            }.padding(.bottom)
//                .listStyle(.plain)
//        }
//    }
//}
//
//struct FriendsView: View {
//    @State private var searchText: String = ""
//
//    var body: some View {
//        VStack {
//            SearchBar(text: $searchText)
//
//            // Add your friends list here using the searchText
//            // Example: List { ... }
//        }
//        .navigationTitle("Friends")
//    }
//}
//
//struct SearcBar: View {
//    @Binding var text: String
//
//    var body: some View {
//        HStack {
//            TextField("Search", text: $text)
//                .padding()
//                .background(Color(.systemGray6))
//                .cornerRadius(8)
//            
//
//            if !text.isEmpty {
//                Button(action: {
//                    text = ""
//                }) {
//                    Image(systemName: "xmark.circle.fill")
//                        .foregroundColor(Color(.systemGray))
//                }
//                .padding(.top)
//                .transition(.move(edge: .top))
//               
//            }
//        }
//    }
//}
//
//struct ProfileTopBarView: View {
//    @ObservedObject var userViewModel = UserViewModel()
//
//    var body: some View {
//        
//        NavigationStack {
//                ProfileTopBar(userViewModel: userViewModel)
//                
//                NavigationLink(destination: FriendsView()) {
//                    Text("My Friends")
//                        .padding()
//                        .foregroundColor(.blue)
//                    
//                        .padding(.bottom)
//                    
//                }
//            }
//        Spacer()
//       
//            .navigationTitle("ptofile")
//        }
//    }
//
//struct ContentView_Previews: PreviewProvider {
//    static var previews: some View {
//        ContentView()
//    }
//}
//
