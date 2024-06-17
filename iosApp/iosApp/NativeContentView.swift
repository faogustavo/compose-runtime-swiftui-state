//
//  NativeContentView.swift
//  iosApp
//
//  Created by Gustavo Valvassori on 16/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import ComposeApp

struct NativeContentView : View {
    
    @ObservedObject var viewModel: CountViewModel
    
    var body: some View {
        VStack {
            Text(String(describing: viewModel.count))
                .font(.title)
                .fontWeight(.bold)
            Text("Current Count")
            
            HStack {
                Button(
                    action: { viewModel.dec() },
                    label: { Text("Decrement") }
                ).padding(.horizontal, 8).tint(.red)
                
                Button(
                    action: { viewModel.inc() },
                    label: { Text("Increment") }
                ).padding(.horizontal, 8)
            }.padding(.vertical, 8)
        }
    }
}
