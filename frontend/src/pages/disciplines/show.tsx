import { useShow, useOne } from "@refinedev/core";
import { Show, TagField, TextField } from "@refinedev/antd";
import { Typography } from "antd";
import { Discipline } from "../../shared/api/types";

const { Title } = Typography;

export const DisciplineShow = () => {
    const { query } = useShow<Discipline>();
    const { data, isLoading } = query;

    const record = data?.data;
    const lecturer = record?.lecturer;

    return (
        <Show isLoading={isLoading}>
            <Title level={5}>Id</Title>
            <TextField value={record?.id} />
            <Title level={5}>Name</Title>
            <TextField value={record?.name} />
            <Title level={5}>Description</Title>
            <TextField value={record?.description} />
            <Title level={5}>Lecturer</Title>
            <TagField value={lecturer?.academicTitle} />
            <TextField value={`${lecturer?.lastName} ${lecturer?.firstName}`} />
            <Title level={5}>Course</Title>
            <TextField value={record?.course.number} />
        </Show>
    );
};
