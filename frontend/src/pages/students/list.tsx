import { BaseRecord } from "@refinedev/core";
import { useTable, List, EditButton, ShowButton, DeleteButton } from "@refinedev/antd";
import { Table, Space } from "antd";

export const StudentList = () => {
    const { tableProps } = useTable({
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
                <Table.Column dataIndex="group" title="Group" />
                <Table.Column dataIndex="course" title="Course" render={(course) => course.number} />
                <Table.Column
                    title="Actions"
                    dataIndex="actions"
                    render={(_, record: BaseRecord) => (
                        <Space>
                            <EditButton hideText size="small" recordItemId={record.id} />
                            <ShowButton hideText size="small" recordItemId={record.id} />
                            <DeleteButton hideText size="small" recordItemId={record.id} />
                        </Space>
                    )}
                />
            </Table>
        </List>
    );
};
