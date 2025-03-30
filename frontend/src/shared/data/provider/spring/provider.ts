import { BaseRecord, DataProvider, GetListParams, GetListResponse } from "@refinedev/core";
import simpleRestDataProvider from "@refinedev/simple-rest";

interface SpringDataResponse<T = BaseRecord> extends GetListResponse {
    content: T[];
    total: number;
}

export const springDataProvider = (
    apiUrl: string,
): Omit<
    Required<DataProvider>,
    "createMany" | "updateMany" | "deleteMany"
> => {
    const delegate = simpleRestDataProvider(apiUrl);
    return {
        ...delegate,
        getList: async <TData extends BaseRecord = BaseRecord>(
            params: GetListParams
        ): Promise<GetListResponse<TData>> => {
            const response = await delegate.getList<TData>(params);
            const springData = response.data as unknown as SpringDataResponse<TData>;

            return {
                data: springData.content,
                total: springData.total || springData.totalElements || 0,
            };
        },
    }
};