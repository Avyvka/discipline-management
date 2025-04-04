import { Create, useForm } from "@refinedev/antd";
import { Form, Input } from "antd";
import { Course } from "../../shared/api/types";

export const CourseCreate = () => {
    const { formProps, saveButtonProps } = useForm<Course>();

    return (
        <Create saveButtonProps={saveButtonProps}>
            <Form {...formProps} layout="vertical">
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
        </Create>
    );
};
