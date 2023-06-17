'use client';
import {useCallback, useState} from "react";
import {FieldValues,
        SubmitHandler,
        useForm} from "react-hook-form";
import Input from "@/app/components/inputs/Input";
import Button from "@/app/components/Button";
import AuthSocialButton from "@/app/(site)/components/AuthSocialButton";
import {BsGithub, BsGoogle} from "react-icons/bs";
import axios from "axios";
import toast from "react-hot-toast";
import {signIn} from "next-auth/react";

type Variant = 'LOGIN' | 'REGISTER';
const MESSENGER_API_URL = "http://localhost:8080";
let access_token;

const AuthForm = () => {
  const [variant, setVariant] = useState<Variant>('LOGIN');
  const [isLoading, setIsLoading] = useState(false);

  const toggleVariant = useCallback(() => {
    if (variant === 'LOGIN') {
      setVariant('REGISTER');
    } else {
      setVariant('LOGIN');
    }
  }, [variant]);

  const {
    register,
      handleSubmit,
      formState: {
      errors
      }
  } = useForm<FieldValues>({
    defaultValues: {
      name: '',
      email: '',
      password: ''
    }
  });

  const onSubmit: SubmitHandler<FieldValues> = (data) => {
    setIsLoading(true);

    if(variant === 'REGISTER') {
      axios.post(`${MESSENGER_API_URL}` + '/api/user/save', data)
          .catch(() => toast.error('Something went wrong!'))
          .finally(() => setIsLoading(false));
    }
    if (variant === 'LOGIN') {
      axios.post(`${MESSENGER_API_URL}` + '/api/user/loginWithPwd', data)
          .then(function (response) {
            access_token = response.data.accessToken;
            toast.success('Logged-in successfully');
          })
          .catch(function (error) {
            if (error.response.status === 403) {
              toast.error('Invalid credentials');
            }
          }).finally(() => setIsLoading(false));
    }
  }

  const socialAction = (action: string) => {
    setIsLoading(true);

    signIn(action, {redirect: false})
        .then((callback) => {
          console.log("Error:" + callback?.error);
          console.log("Status:" + callback?.status);
          console.log("Ok:" + callback?.ok);
          console.log("URL:" + callback?.url);
          if (callback?.error) {
            toast.error('Invalid credentials');
          }
          if (callback?.ok && !callback?.error) {
            toast.success('Logged-in successfully');
          }
        }).finally(() => setIsLoading(false));
  }

  return (
      <div className="mt-8
       sm:mx-auto
       sm:w-full
       sm:max-w-md">
        <div className="bg-white
        px-4
        py-8
        shadow
        sm:rounded-lg
        sm:px-10">
          <form className="space-y-6" onSubmit={handleSubmit(onSubmit)}>
            {variant === 'REGISTER' && (<Input label="Name" id="name" register={register} errors={errors} disabled={isLoading}/>)}
            <Input label="Email Address" id="email" type="email" register={register} errors={errors} disabled={isLoading}/>
            <Input label="Password" id="password" type="password" register={register} errors={errors} disabled={isLoading}/>
            <div>
              <Button disabled={isLoading} fullWidth type="submit">
                {variant === 'LOGIN' ? 'Sign in' : 'Register'}
              </Button>
            </div>


          </form>
          <div className="mt-6">
            <div className="relative">
              <div className="absolute inset-0 flex items-center">
                <div className="w-full border-t border-gray-300"></div>
              </div>
              <div className="relative flex justify-center text-sm">
                  <span className="bg-white px-2 text-gray-500">Or continue with</span>
              </div>
            </div>
            <div className="mt-6 flex gap-2">
              <AuthSocialButton icon={BsGithub} onClick={() => socialAction('github')}/>
              <AuthSocialButton icon={BsGoogle} onClick={() => socialAction('google')}/>
            </div>
          </div>
          <div className="flex gap-2 justify-center text-sm
          mt-6 px-2 text-gray-500">
            <div>
              {variant === 'LOGIN' ? 'New To Messenger?' : 'Already have an account?'}
            </div>
            <div onClick={toggleVariant} className="underline cursor-pointer">
              {variant === 'LOGIN' ? 'Create an account' : 'Login'}
            </div>
          </div>
        </div>
      </div>
  );
}

export default AuthForm;