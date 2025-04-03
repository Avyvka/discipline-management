import { useShow } from "@refinedev/core";
import { Show, TextField } from "@refinedev/antd";
import { List, Typography } from "antd";
import { Course } from "../../shared/api/types";

const { Title } = Typography;

export const CourseShow = () => {
    const { query } = useShow<Course>();
    const { data, isLoading } = query;
    const record = data?.data;

    return (
        <Show isLoading={isLoading}>
            <Title level={5}>Id</Title>
            <TextField value={record?.id} />
            <Title level={5}>Number</Title>
            <TextField value={record?.number ?? ""} />
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
