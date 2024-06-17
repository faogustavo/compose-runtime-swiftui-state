import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
	var body: some Scene {
		WindowGroup {
            TabView {
                ContentView()
                    .tabItem {
                        Image(systemName: "heart.fill")
                        Text("Compose")
                    }
                NativeContentView(viewModel: CountViewModel())
                    .tabItem {
                        Image(systemName: "person.fill")
                        Text("Native")
                    }
            }
		}
	}
}
