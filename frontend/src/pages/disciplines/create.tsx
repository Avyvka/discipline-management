import { Create, useForm, useSelect } from "@refinedev/antd";
import { Form, Input, Select } from "antd";
import { Discipline, Lecturer, Course } from "../../shared/api/types";

export const DisciplineCreate = () => {
    const { formProps, saveButtonProps, query } = useForm<Discipline>();

    const disciplinesData = query?.data?.data;

    const { selectProps: lecturerSelectProps } = useSelect<Lecturer>({
        resource: "lecturers",
        optionLabel: (e) => `${e.lastName} ${e.firstName[0]}. (${e.academicTitle})`,
        optionValue: "id",
        defaultValue: disciplinesData?.lecturer?.id,
    });

    const { selectProps: courseSelectProps } = useSelect<Course>({
        resource: "courses",
        optionLabel: "number",
        optionValue: "id",
        defaultValue: disciplinesData?.course?.id,
    });

    return (
        <Create saveButtonProps={saveButtonProps}>
            <Form {...formProps} layout="vertical">
                <Form.Item
                    label="Name"
                    name={["name"]}
                    rules={[
                        {
                            required: true,
                        },
                    ]}
                >
                    <Input />
                </Form.Item>
                <Form.Item
                    label="Description"
                    name={["description"]}
                    rules={[
                        {
                            required: true,
                        },
                    ]}
                >
                    <Input />
                </Form.Item>
                <Form.Item
                    label="Lecturer"
                    name={["lecturer", "id"]}
                    rules={[
                        {
                            required: true,
                        },
                    ]}
                >
                    <Select {...lecturerSelectProps} />
                </Form.Item>
                <Form.Item
                    label="Course"
                    name={["course","id"]}
                    rules={[
                        {
                            required: true,
                        },
                    ]}
                >
                    <Select {...courseSelectProps} />
                </Form.Item>
            </Form>
        </Create>
    );
};
