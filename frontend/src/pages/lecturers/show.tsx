import { useShow } from "@refinedev/core";
import { Show, TextField, NumberField } from "@refinedev/antd";
import { List, Typography } from "antd";
import { Lecturer } from "../../shared/api/types";

const { Title } = Typography;

export const LecturerShow = () => {
    const { query } = useShow<Lecturer>();
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
            <Title level={5}>Academic Title</Title>
            <TextField value={record?.academicTitle} />
            <Title level={5}>Disciplines</Title>
            <List
                dataSource={record?.disciplines}
                renderItem={(item) => (
                    <List.Item>
                        <TextField value={item.name} />
                    </List.Item>
                )}
            />
        </Show>
    );
};
