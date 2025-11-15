db = db.getSiblingDB('payapp');

// USERS
db.createCollection('users', {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: ["_id","plan","registeredAt"],
      properties: {
        _id: { bsonType: "string", description: "tg_id" },
        tgUsername: { bsonType: ["string","null"], maxLength: 49 },
        tgFirstName: { bsonType: ["string","null"], maxLength: 255 },
        tgLastName: { bsonType: ["string","null"], maxLength: 255 },
        plan: { enum: ["free","prem"] },
        tgPfp: { bsonType: ["string","null"] },
        tgPfpId: { bsonType: ["string","null"], maxLength: 128 },
        registeredAt: { bsonType: "date" }
      }
    }
  }
});
db.users.createIndex({ tgUsername: 1 }, { unique: true, sparse: true });

// SAVED_DETAILS
db.createCollection('saved_details', {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: ["tgId","type","details","createdAt","updatedAt"],
      properties: {
        tgId: { bsonType: "string" },
        type: { enum: ["bank","sbp","wallet","crypto"] },
        details: { bsonType: "object" },
        createdAt: { bsonType: "date" },
        updatedAt: { bsonType: "date" }
      }
    }
  }
});
db.saved_details.createIndex({ tgId:1, type:1 });

// INVOICES
db.createCollection('invoices', {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: ["_id","ownerId","type","status","sum","receiversQuantity","createdAt"],
      properties: {
        _id: { bsonType: "string", description: "UUID" },
        ownerId: { bsonType: "string" },
        type: { enum: ["solo","multi"] },
        status: { enum: ["verified","pending","waiting"] },
        sum: { bsonType: "decimal" },
        receiversQuantity: { bsonType: "int", minimum: 1 },
        description: { bsonType: ["string","null"], maxLength: 255 },
        deadline: { bsonType: ["date","null"] },
        banks: { bsonType: ["object","null"] },
        sbp: { bsonType: ["object","null"] },
        wallets: { bsonType: ["object","null"] },
        crypto: { bsonType: ["object","null"] },
        link: { bsonType: ["string","null"] },
        createdAt: { bsonType: "date" },
        paidAt: { bsonType: ["date","null"] },
        paidBy: { bsonType: ["string","null"] }
      }
    }
  }
});
db.invoices.createIndex({ ownerId:1, status:1 });

// INVOICE_RECEIVERS
db.createCollection('invoice_receivers', {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: ["invoiceId","receiverId","accepted","joinedAt"],
      properties: {
        invoiceId: { bsonType: "string" },
        receiverId: { bsonType: "string" },
        accepted: { bsonType: "bool" },
        joinedAt: { bsonType: "date" }
      }
    }
  }
});
db.invoice_receivers.createIndex({ invoiceId:1, receiverId:1 }, { unique: true });

// PYRO_SESSIONS
db.createCollection('pyro_sessions', {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: ["name","status","isAlive"],
      properties: {
        name: { bsonType: "string", maxLength: 50 },
        status: { enum: ["idle","busy","error"] },
        lastUsed: { bsonType: ["date","null"] },
        requestCount: { bsonType: ["long","int","null"], minimum: 0 },
        lastError: { bsonType: ["string","null"] },
        isAlive: { bsonType: "bool" }
      }
    }
  }
});
db.pyro_sessions.createIndex({ name:1 }, { unique: true });
db.pyro_sessions.createIndex({ status:1 });

// HUNTED_BASE
db.createCollection('hunted_base', {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: ["_id","addedAt"],
      properties: {
        _id: { bsonType: "string" },
        tgUsername: { bsonType: ["string","null"], maxLength: 49 },
        tgFirstName: { bsonType: ["string","null"], maxLength: 255 },
        tgLastName: { bsonType: ["string","null"], maxLength: 255 },
        tgPfp: { bsonType: ["string","null"] },
        tgPfpId: { bsonType: ["string","null"], maxLength: 128 },
        addedAt: { bsonType: "date" }
      }
    }
  }
});
db.hunted_base.createIndex({ tgUsername:1 }, { unique: true, sparse: true });

// USER_ACTIVITY
db.createCollection('user_activity', {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: ["tgId","activityTime"],
      properties: {
        tgId: { bsonType: "string" },
        activityType: { bsonType: ["string","null"], maxLength: 50 },
        activityTime: { bsonType: "date" },
        metadata: { bsonType: ["object","null"] }
      }
    }
  }
});
db.user_activity.createIndex({ tgId:1, activityTime:-1 });
