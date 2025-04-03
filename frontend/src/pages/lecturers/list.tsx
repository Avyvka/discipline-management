import { BaseRecord } from "@refinedev/core";
import {
    useTable,
    List,
    EditButton,
    ShowButton,
    DeleteButton,
} from "@refinedev/antd";
import { Table, Space } from "antd";
import { Lecturer } from "../../shared/api/types";

export const LecturerList = () => {
    const { tableProps } = useTable<Lecturer>({
        syncWithLocation: true,
    });

    return (
        <List>
            <Table {...tableProps} rowKey="id">
                <Table.Column dataIndex="id" title="Id" />
                <Table.Column dataIndex="firstName" title="First Name" />
                <Table.Column dataIndex="lastName" title="Last Name" />
                <Table.Column dataIndex="middleName" title="Middle Name" />
                <Table.Column dataIndex="age" title="Age" />
                <Table.Column
                    dataIndex="academicTitle"
                    title="Academic Title"
                />
                <Table.Column
                    title="Actions"
                    dataIndex="actions"
                    render={(_, record: BaseRecord) => (
                        <Space>
                            <EditButton
                                hideText
                                size="small"
                                recordItemId={record.id}
                            />
                            <ShowButton
                                hideText
                                size="small"
                                recordItemId={record.id}
                            />
                            <DeleteButton
                                hideText
                                size="small"
                                recordItemId={record.id}
                            />
                        </Space>
                    )}
                />
            </Table>
        </List>
    );
};
