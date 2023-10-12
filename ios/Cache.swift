import GRDB
import Foundation

@objc(KitCacheManager)
final class KitCacheManager: NSObject {
    
    struct StringPayload: Codable, FetchableRecord, PersistableRecord {
        let id: String
        let value: String
        
        static var databaseTableName: String {
            "string_payload"
        }
    }
    
    static let dbQueue: DatabaseQueue = {
        let fm = FileManager.default
        let dbPath = try! fm
            .url(for: .applicationSupportDirectory, in: .userDomainMask, appropriateFor: nil, create: true)
            .appendingPathComponent("kit.sqlite")
            .path
        print("dbPath: \(dbPath)")
        let dbQueue = try! DatabaseQueue(path: dbPath)
        return dbQueue
    }()
    
    override init() {
        super.init()
        do {
            try Self.dbQueue.write { db in
                try db.create(table: "string_payload") { t in
                    t.column("id", .text).primaryKey()
                    t.column("value", .text)
                }
            }
        } catch {
            print("Failed to create table", error.localizedDescription)
        }
    }
    
    // MARK: - Cache
    
    @objc(write:withValue:withResolver:withRejecter:)
    func write(id: String, value: String, resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        do {
            try Self.dbQueue.write { db in
              try StringPayload(id: id, value: value).upsert(db)
            }
            resolve(true)
        } catch {
            print("Error", error.localizedDescription)
            reject("write_error", "Failed to write to cache", error)
        }
    }
    
    @objc(read:withResolver:withRejecter:)
    func read(id: String, resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        do {
            let value = try Self.dbQueue.read { db in
                try StringPayload.fetchOne(db, key: id)?.value
            }
            resolve(value)
        } catch {
            print("Error", error.localizedDescription)
            reject("read_error", "Failed to read from cache", error)
        }
    }
    
    @objc(delete:withResolver:withRejecter:)
    func delete(id: String, resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        do {
            let result = try Self.dbQueue.write { db in
                try StringPayload.deleteOne(db, key: id)
            }
            resolve(result)
        } catch {
            print("Error", error.localizedDescription)
            reject("delete_error", "Failed to delete from cache", error)
        }
    }
    
    @objc(clear:withRejecter:)
    func clear(resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        do {
            let result = try Self.dbQueue.write { db in
                try StringPayload.deleteAll(db)
            }
            resolve(result)
        } catch {
            print("Error", error.localizedDescription)
            reject("clear_error", "Failed to clear cache", error)
        }
    }
    
}
