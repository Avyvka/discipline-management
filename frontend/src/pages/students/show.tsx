import { useShow, useOne } from "@refinedev/core";
import { Show, TextField, NumberField } from "@refinedev/antd";
import { Typography } from "antd";
import { Student } from "../../shared/api/types";

const { Title } = Typography;

export const StudentShow = () => {
    const { query } = useShow<Student>();
    const { data, isLoading } = query;

    const record = data?.data;

    return (
        <Show isLoading={isLoading}>
            <Title level={5}>Id</Title>
            <TextField value={record?.id} />
            <Title level={5}>First Name</Title>
            <TextField value={record?.firstName} />
            <Title level={5}>Last Name</Title>
            <TextField value={record?.lastName} />
            <Title level={5}>Middle Name</Title>
            <TextField value={record?.middleName} />
            <Title level={5}>Age</Title>
            <NumberField value={record?.age ?? ""} />
            <Title level={5}>Group</Title>
            <TextField value={record?.group ?? ""} />
            <Title level={5}>Course</Title>
            <NumberField value={record?.course?.number ?? ""} />
        </Show>
    );
};
