import React from "react";
import { BaseRecord, useMany } from "@refinedev/core";
import { useTable, List, EditButton, ShowButton, DeleteButton } from "@refinedev/antd";
import { Table, Space } from "antd";
import { Discipline } from "../../shared/api/types";

export const DisciplineList = () => {
    const { tableProps } = useTable<Discipline>({
        syncWithLocation: true,
    });

    return (
        <List>
            <Table {...tableProps} rowKey="id">
                <Table.Column dataIndex="id" title="Id" />
                <Table.Column dataIndex="name" title="Name" />
                <Table.Column dataIndex="description" title="Description" />
                <Table.Column dataIndex="course" title="Course" render={(course) => course.number} />
                <Table.Column
                    dataIndex="lecturer"
                    title="Lecturer"
                    render={(lecturer) => `${lecturer.lastName} ${lecturer.firstName[0]}.`}
                />
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
