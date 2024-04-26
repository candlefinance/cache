import Foundation
import PINCache

@objc(KitCacheManager)
final class KitCacheManager: NSObject {
    
    struct StringPayload: Codable {
        let id: String
        let value: String
        
        static var databaseTableName: String {
            "string_payload"
        }
    }
    
    // MARK: - Cache
    
    @objc(write:withValue:withResolver:withRejecter:)
    func write(id: String, value: String, resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        PINCache.shared.setObject(value, forKey: id)
        resolve(true)
    }
    
    @objc(read:withResolver:withRejecter:)
    func read(id: String, resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        let value = PINCache.shared.object(forKey: id)
        resolve(value)
    }
    
    @objc(delete:withResolver:withRejecter:)
    func delete(id: String, resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        PINCache.shared.removeObject(forKey: id)
        resolve(true)
    }
    
    @objc(clear:withRejecter:)
    func clear(resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        PINCache.shared.removeAllObjects()
        resolve(true)
    }
    
}
