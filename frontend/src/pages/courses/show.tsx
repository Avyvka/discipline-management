import { useShow } from "@refinedev/core";
import { Show, TextField } from "@refinedev/antd";
import { Typography } from "antd";

const { Title } = Typography;

export const CourseShow = () => {
    const { query } = useShow();
    const { data, isLoading } = query;

    const record = data?.data;

    return (
        <Show isLoading={isLoading}>
            <Title level={5}>Id</Title>
            <TextField value={record?.id} />
            <Title level={5}>Number</Title>
            <TextField value={record?.number ?? ""} />
        </Show>
    );
};
