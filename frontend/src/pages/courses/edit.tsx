import { Edit, useForm } from "@refinedev/antd";
import { Form, Input } from "antd";
import { Course } from "../../shared/api/types";

export const CourseEdit = () => {
    const { formProps, saveButtonProps } = useForm<Course>();
    return (
        <Edit saveButtonProps={saveButtonProps}>
            <Form {...formProps} layout="vertical">
                <Form.Item
                    label="Id"
                    name={["id"]}
                    rules={[
                        {
                            required: true,
                        },
                    ]}
                >
                    <Input readOnly disabled />
                </Form.Item>
                <Form.Item
                    label="Number"
                    name={["number"]}
                    rules={[
                        {
                            required: true,
                        },
                    ]}
                >
                    <Input />
                </Form.Item>
            </Form>
        </Edit>
    );
};
