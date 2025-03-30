import { useTable, List, EditButton, ShowButton, DeleteButton } from "@refinedev/antd";
import { Table, Space } from "antd";
import { Course } from "../../shared/api/types";

export const CourseList = () => {
    const { tableProps } = useTable<Course>({
        syncWithLocation: true,
    });

    return (
        <List>
            <Table {...tableProps} rowKey="id">
                <Table.Column dataIndex="id" title="Id" />
                <Table.Column dataIndex="number" title="Number" />
                <Table.Column
                    title="Actions"
                    dataIndex="actions"
                    render={(_, record: Course) => (
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
