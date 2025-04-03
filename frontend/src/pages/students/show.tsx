import { useShow, useOne } from "@refinedev/core";
import { Show, TextField, NumberField } from "@refinedev/antd";
import { Typography } from "antd";

const { Title } = Typography;

export const StudentShow = () => {
    const { query } = useShow();
    const { data, isLoading } = query;

    const record = data?.data;

    const { data: courseData, isLoading: courseIsLoading } = useOne({
        resource: "courses",
        id: record?.course?.id || "",
        queryOptions: {
            enabled: !!record,
        },
    });

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
            <NumberField value={record?.group ?? ""} />
            <Title level={5}>Course</Title>
            {courseIsLoading ? <>Loading...</> : <>{courseData?.data?.number}</>}
        </Show>
    );
};
