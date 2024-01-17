//
//  MashaelAppApp.swift
//  MashaelApp
//
//  Created by Foundation 43 on 08/05/1445 AH.
//

import SwiftUI

@main
struct MashaelAppApp: App {
    var body: some Scene {
        
        WindowGroup {
            ContentView()
        }
        .modelContainer(for :data.self)
    }
}
