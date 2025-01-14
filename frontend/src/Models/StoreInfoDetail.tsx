export type StoreInfoDetail = {
    companyName: String,
    storeCode: String,
    storeName: String,
    storeType: String,
    coreStatus: String,
    networkStatus: String,
    gatewayStatus: String,
    syncStatus: String,
    activeDeviceType: String,
    posResultPercent: number,
    tagTotal: number,
    tagConnected: number,
    updateTime: String,
    storeInfo: {
        companyName: String,
        storeCode: String,
        storeName: String,
        ipAddrPrev: String,
        ipAddrNow: String,
        createdDate: String,
        updatedDate: String
    }
}