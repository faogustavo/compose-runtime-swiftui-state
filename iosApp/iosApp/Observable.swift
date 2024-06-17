//
//  Observable.swift
//  iosApp
//
//  Created by Gustavo Valvassori on 16/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import ComposeApp
import Combine
import SwiftUI

typealias CancellationToken = () -> Void

extension LibKotlinObservableViewModel: Combine.ObservableObject {
    private static var objectWillChangePublisherKey: UInt8 = 0
    private static var objectWillChangeTokenKey: UInt8 = 0

    public var objectWillChange: ObservableObjectPublisher {
        if let publisher = objc_getAssociatedObject(self, &Self.objectWillChangePublisherKey) as? ObservableObjectPublisher {
            return publisher
        }
        let publisher = ObjectWillChangePublisher()
        objc_setAssociatedObject(self, &Self.objectWillChangePublisherKey, publisher, objc_AssociationPolicy.OBJC_ASSOCIATION_RETAIN)
        if let previousToken = objc_getAssociatedObject(self, &Self.objectWillChangePublisherKey) as? CancellationToken {
            previousToken()
        }
        let cancelationToken = changeTracking.addWillChangeObserver {
            publisher.send()
        }
        objc_setAssociatedObject(self, &Self.objectWillChangeTokenKey, cancelationToken, objc_AssociationPolicy.OBJC_ASSOCIATION_RETAIN)
        return publisher
    }
}

extension LibKotlinObservableViewModel: Identifiable {}


